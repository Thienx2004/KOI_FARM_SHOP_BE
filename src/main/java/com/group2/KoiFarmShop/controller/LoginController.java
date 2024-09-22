package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.Content;
import com.group2.KoiFarmShop.dto.reponse.AccountReponse;
import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.IntrospectResponse;
import com.group2.KoiFarmShop.dto.request.IntrospectRequest;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import com.group2.KoiFarmShop.service.AuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.beans.Encoder;
import java.util.Map;

@RestController
//@RequestMapping("/koi")
public class LoginController {

    @Autowired
    private AccountServiceImp accountServiceImp;

    @PostMapping("/signin")
    public ApiReponse<Content> login(@RequestBody LoginRequest loginRequest) {
        ApiReponse<Content> reponse = accountServiceImp.login(loginRequest);
        return reponse;
    }
    @PostMapping("/signingoogle")
    public ApiReponse<Content> logingoogle(@RequestBody String token) {
        ApiReponse<Content> reponse = accountServiceImp.logingg(token);
        return reponse;
    }
}

