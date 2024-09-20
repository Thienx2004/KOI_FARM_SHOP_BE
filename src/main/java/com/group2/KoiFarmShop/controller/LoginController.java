package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.AccountReponse;
import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.beans.Encoder;

@RestController
//@RequestMapping("/koi")
public class LoginController {

    @Autowired
    private AccountServiceImp accountServiceImp;

    @PostMapping("/signin")
    public ApiReponse<AccountReponse> login(@RequestBody LoginRequest loginRequest) {
        ApiReponse apiReponse = accountServiceImp.login(loginRequest);
        return apiReponse;
    }


}

