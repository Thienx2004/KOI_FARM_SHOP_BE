package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.response.ApiReponse;
import com.group2.KoiFarmShop.dto.response.ConsignmentDetailResponse;
import com.group2.KoiFarmShop.dto.response.ConsignmentResponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.service.ConsignmentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/consignment")
public class ConsignmentController {

    @Autowired
    private ConsignmentServiceImp consignmentService;

    @PostMapping("/createConsignment")
    public ApiReponse<String> createConsignment(@RequestParam int accountId, @RequestParam MultipartFile koiImg,
                                                @RequestParam String origin,
                                                @RequestParam boolean gender,
                                                @RequestParam int age,
                                                @RequestParam double size,
                                                @RequestParam String personality,
                                                @RequestParam double price,
                                                @RequestParam int categoryId,
                                                @RequestParam String name,
                                                @RequestParam MultipartFile certImg,
                                                @RequestParam String notes,
                                                @RequestParam String phoneNumber,
                                                @RequestParam boolean consignmentType,
                                                @RequestParam boolean online
                                                ){

        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(consignmentService.createConsignment(accountId, koiImg, origin, gender, age, size, personality, price, categoryId, name, certImg ,notes, phoneNumber, consignmentType, online));

        return apiReponse;
    }

    @PutMapping("/approve/{consignmentID}")
    public ApiReponse<String> approveConsignment(@PathVariable int consignmentID){

        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(consignmentService.approveConsignment(consignmentID));

        return apiReponse;
    }

    @PutMapping("/reject/{consignmentID}")
    public ApiReponse<String> rejectConsignment(@PathVariable int consignmentID, @RequestParam String rejectionReason){
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(consignmentService.rejectConsignment(consignmentID, rejectionReason));

        return apiReponse;
    }

    @GetMapping("/getAllConsignment")
    public ApiReponse<PaginReponse<ConsignmentResponse>> getAllConsignmentForCustomer(@RequestParam int pageNo, @RequestParam int pageSize,@RequestParam int accountId){
        ApiReponse apiReponse = new ApiReponse();
        PaginReponse<ConsignmentResponse> consignmentResponse = consignmentService.getAllConsignmentForCustomer(pageNo, pageSize, accountId);
        apiReponse.setData(consignmentResponse);
        return apiReponse;
    }

    @GetMapping("/getAllConsignmentManagement")
    public ApiReponse<PaginReponse<ConsignmentResponse>> getAllConsignmentForStaff(@RequestParam int pageNo, @RequestParam int pageSize){
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


}