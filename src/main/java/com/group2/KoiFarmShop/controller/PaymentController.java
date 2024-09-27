package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.PaymentDTO;
import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ApiReponse<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return ApiReponse.<PaymentDTO.VNPayResponse>builder().statusCode(200).message("Thanh cong").data(paymentService.createVnPayPayment(request)).build();

    }
    @GetMapping("/vn-pay-callback")
    public ApiReponse<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            return ApiReponse.<PaymentDTO.VNPayResponse>builder().statusCode(200).message("Thanh toán thành công").data(PaymentDTO.VNPayResponse.builder().code("00").message("Thanh cong").paymentUrl("").build()).build();
        } else {
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
    }
}
