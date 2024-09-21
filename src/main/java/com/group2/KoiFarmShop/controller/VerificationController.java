package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.IntrospectResponse;
import com.group2.KoiFarmShop.dto.request.IntrospectRequest;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.VerificationToken;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.repository.VerificationTokenRepository;
import com.group2.KoiFarmShop.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class VerificationController {

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/verify")
    public ApiReponse<String> verifyEmail(@RequestParam String token) {
        Optional<VerificationToken> verificationToken = tokenRepository.findByToken(token);
        ApiReponse apiReponse = new ApiReponse();

        if (!verificationToken.isPresent()) {
            apiReponse.setStatusCode(411);
            apiReponse.setMessage("Token này không hợp lệ.");
            return apiReponse;
        }

        VerificationToken tokenEntity = verificationToken.get();
        Account user = tokenEntity.getAccount();

        // Kiểm tra thời gian hết hạn của token
        if (tokenEntity.getExpiryDate().isBefore(LocalDateTime.now())) {
            apiReponse.setStatusCode(412);
            apiReponse.setMessage("Token này đã hết hạn");
            return apiReponse;
        }

        // Xác thực người dùng
        user.setVerified(true);
        accountRepository.save(user);

        apiReponse.setMessage("Xác thực email thành công!");
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
