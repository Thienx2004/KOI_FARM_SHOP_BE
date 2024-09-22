package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.IntrospectResponse;
import com.group2.KoiFarmShop.dto.request.IntrospectRequest;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.VerificationToken;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.repository.VerificationTokenRepository;
import com.group2.KoiFarmShop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class VerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @PostMapping("/verify-otp")
    public ApiReponse<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        ApiReponse apiReponse = accountService.verifyOTP(email, otp);


        return apiReponse;
    }

    @PostMapping("/resend-otp")
    public ApiReponse<String> resendOtp(@RequestParam String email) {
        ApiReponse apiReponse = accountService.resendOTP(email);


        return apiReponse;
    }
    @PostMapping("/introspect")
    public ApiReponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) {
        IntrospectResponse introspect= authenticationService.introspect(request);
        ApiReponse<IntrospectResponse> response= new ApiReponse<>();
        response.setData(introspect);
        return response;
    }

}
