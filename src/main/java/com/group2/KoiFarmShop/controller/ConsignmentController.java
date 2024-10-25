package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.request.*;
import com.group2.KoiFarmShop.dto.response.*;
import com.group2.KoiFarmShop.entity.Consignment;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.entity.Orders;
import com.group2.KoiFarmShop.entity.Payment;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.ConsignmentRepository;
import com.group2.KoiFarmShop.repository.PaymentRepository;
import com.group2.KoiFarmShop.service.ConsignmentServiceImp;
import com.group2.KoiFarmShop.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/consignment")
public class ConsignmentController {

    @Autowired
    private ConsignmentServiceImp consignmentService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ConsignmentRepository consignmentRepository;
    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/createConsignment",consumes = "multipart/form-data")
    public ApiReponse<Integer> createConsignment(@RequestParam int accountId, @RequestParam(required = false) MultipartFile koiImg,
                                                 @RequestParam(required = false) String koiImgURL,
                                                @RequestParam String origin,
                                                @RequestParam boolean gender,
                                                @RequestParam int age,
                                                @RequestParam double size,
                                                @RequestParam String personality,
                                                @RequestParam double price,
                                                @RequestParam String food,
                                                @RequestParam String health,
                                                @RequestParam String ph,
                                                @RequestParam String temperature,
                                                @RequestParam String water,
                                                @RequestParam int pureBred,
                                                @RequestParam int categoryId,
                                                @RequestParam String name,
                                                @RequestParam(required = false) MultipartFile certImg,
                                                @RequestParam(required = false) String certImgURL,
                                                @RequestParam String notes,
                                                @RequestParam String phoneNumber,
                                                @RequestParam boolean consignmentType,
                                                @RequestParam int duration,
                                                @RequestParam double serviceFee,
                                                @RequestParam boolean online
    ) {

        ApiReponse<Integer> apiReponse = new ApiReponse<>();
        apiReponse.setData(consignmentService.createConsignment(accountId, koiImg,koiImgURL, origin, gender, age, size, personality, price, food, health, ph, temperature, water,
                pureBred, categoryId, name, certImg,certImgURL, notes, phoneNumber, consignmentType, duration, serviceFee, online));

        return apiReponse;
    }

    @PutMapping("/approve/{consignmentID}")
    public ApiReponse<String> approveConsignment(@PathVariable int consignmentID) throws MessagingException {

        ApiReponse<String> apiReponse = new ApiReponse<>();
        apiReponse.setData(consignmentService.approveConsignment(consignmentID));

        return apiReponse;
    }

    @PutMapping("/reject/{consignmentID}")
    public ApiReponse<String> rejectConsignment(@PathVariable int consignmentID, @RequestParam String rejectionReason) throws MessagingException {
        ApiReponse<String> apiReponse = new ApiReponse<>();
        apiReponse.setData(consignmentService.rejectConsignment(consignmentID, rejectionReason));

        return apiReponse;
    }

    @GetMapping("/getAllConsignment")
    public ApiReponse<PaginReponse<ConsignmentResponse>> getAllConsignmentForCustomer(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam int accountId) {
        ApiReponse apiReponse = new ApiReponse();
        PaginReponse<ConsignmentResponse> consignmentResponse = consignmentService.getAllConsignmentForCustomer(pageNo, pageSize, accountId);
        apiReponse.setData(consignmentResponse);
        return apiReponse;
    }

    @GetMapping("/getAllConsignmentManagement")
    public ApiReponse<PaginReponse<ConsignmentResponse>> getAllConsignmentForStaff(@RequestParam int pageNo, @RequestParam int pageSize) {
        ApiReponse apiReponse = new ApiReponse();
        PaginReponse<ConsignmentResponse> consignmentResponse = consignmentService.getAllConsignmentForStaff(pageNo, pageSize);
        apiReponse.setData(consignmentResponse);
        return apiReponse;
    }

    @GetMapping("/consignmentDetail/{consignmentId}")
    public ApiReponse<ConsignmentDetailResponse> getConsignmentDetail(@PathVariable int consignmentId) {
        ApiReponse<ConsignmentDetailResponse> response = new ApiReponse<>();
        response.setData(consignmentService.getConsignmentDetail(consignmentId));
        return response;
    }

    @GetMapping("/checkDate/{consignmentId}")
    public ApiReponse<ConsignmentResponse> checkPaymentDate(@PathVariable int consignmentId) {
        ApiReponse<ConsignmentResponse> apiReponse = new ApiReponse<>();
        apiReponse.setData(consignmentService.processPayment(consignmentId, false));
        return apiReponse;

    }

    @PostMapping("/processPayment")
    public ApiReponse<String> processPayment(@RequestParam int consignmentId, @RequestParam String transactionCode) throws MessagingException {
        ApiReponse<String> resp = new ApiReponse<>();
        Payment payment = paymentRepository.findPaymentByTransactionCode(transactionCode).orElseThrow(() -> new AppException(ErrorCode.PAYMENT_FAILED));
        if (!payment.isStatus()) {
            ConsignmentResponse success = consignmentService.processPayment(consignmentId, true);
            Consignment consignment = consignmentRepository.findConsignmentByConsignmentID(success.getConsignmentID()).orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));
            if (success == null) {
                throw new AppException(ErrorCode.PAYMENT_FAILED);
            }
            payment.setConsignment(consignment);
            payment.setStatus(true);
            paymentRepository.save(payment);
            // Gửi email xác nhận sau khi đơn hàng đã lưu thành công
            emailService.sendOrderConfirmationEmail(payment.getConsignment().getAccount().getEmail(), payment.getTransactionCode());
            resp.setData("Thanh toán thành công! Cá đã được đưa lên bán.");
            return resp;
        } else {
            throw new AppException(ErrorCode.TRANSACTION_INVALID);
        }
    }

    @DeleteMapping("/deleteConsignment")
    public ApiReponse<String> deleteConsignment(@RequestParam int consignmentId) {
        ApiReponse<String> apiReponse = new ApiReponse<>();
        apiReponse.setData(consignmentService.deleteConsignment(consignmentId));

        return apiReponse;
    }

    @GetMapping("/sendMail/{consignmentId}")
    public ApiReponse<String> sendMail(@PathVariable int consignmentId) throws MessagingException {
        ApiReponse<String> apiReponse = new ApiReponse<>();
        Consignment consignment = consignmentRepository.findConsignmentByConsignmentID(consignmentId)
                        .orElseThrow(() -> new AppException(ErrorCode.CONSIGNMENT_NOT_FOUND));
        if(consignment != null) {
            emailService.sendEmailCheckKoiToCustomer(consignment.getAccount().getEmail(), consignmentId);
            apiReponse.setData("Gửi mail thành công!");
        }
        return apiReponse;
    }

//    @PutMapping("/update/{id}/{koiId}")
//    public ApiReponse<ConsignmentDetailResponse> updateConsignment(@PathVariable int id, @PathVariable int koiId, @ModelAttribute ConsignmentKoiRequest consignmentRequest) throws MessagingException, IOException {
//
////        ConsignmentDetailResponse consignmentDetailResponse = consignmentService.updateConsignment(consignmentRequest, koiRequest, id, koiId);
//        return ApiReponse.<ConsignmentDetailResponse>builder().data(consignmentDetailResponse).build();
//    }


    @PutMapping("/update/{id}/{koiId}")
    public ApiReponse<ConsignmentDetailResponse> updateConsignment(@PathVariable int id, @PathVariable int koiId, @ModelAttribute ConsignmentKoiRequest consignmentRequest) throws MessagingException, IOException {

        ConsignmentDetailResponse consignmentDetailResponse = consignmentService.updateConsignment(consignmentRequest, id, koiId);
        return ApiReponse.<ConsignmentDetailResponse>builder().data(consignmentDetailResponse).build();
    }

    @PutMapping("/updateHealth")
    public ApiReponse<HealthcareResponse> updateHealth(@RequestBody ConsignmentKoiCare consignmentKoiCare) throws MessagingException, IOException {

        HealthcareResponse healthcareResponse = consignmentService.updateHealth(consignmentKoiCare);
        return ApiReponse.<HealthcareResponse>builder().data(healthcareResponse).build();
    }

    @PostMapping("/addHealth")
    public ApiReponse<HealthcareResponse> addHealth(@RequestBody ConsignmentKoiCare consignmentKoiCare) throws MessagingException, IOException {
    HealthcareResponse healthcareResponse = consignmentService.addHealth(consignmentKoiCare);
    return ApiReponse.<HealthcareResponse>builder().data(healthcareResponse).build();
    }

    @GetMapping("/getAllConsignmentCare")
    public ApiReponse<PaginReponse<ConsignmentResponse>> getAllConsignmentForCare(@RequestParam int pageNo, @RequestParam int pageSize) {
        ApiReponse apiReponse = new ApiReponse();
        PaginReponse<ConsignmentResponse> consignmentResponse = consignmentService.getAllConsignmentForCare(pageNo, pageSize);
        apiReponse.setData(consignmentResponse);
        return apiReponse;
    }

    @GetMapping("/getAllFishCare")
    public ApiReponse<KoiFishPageResponse> getAllFishForCare(@RequestParam int pageNo, @RequestParam int pageSize) {
        KoiFishPageResponse koiFishPageResponse = consignmentService.getFishCare(pageNo, pageSize);
        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishPageResponse).build();
    }
    @GetMapping("/getAllFishCareForCustomer")
    public ApiReponse<KoiFishPageResponse> getAllFishCareForCustomer(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam int accountId) {

        KoiFishPageResponse koiFishPageResponse = consignmentService.getAllFishCareForCustomer(pageNo, pageSize, accountId);

        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishPageResponse).build();
    }
}
