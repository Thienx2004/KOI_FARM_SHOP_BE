package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.Content;
import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.request.LoginGoogleRequest;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
    public ApiReponse<Content> logingoogle(@RequestBody LoginGoogleRequest loginGoogleRequest) {
        ApiReponse<Content> reponse = accountServiceImp.logingg(loginGoogleRequest);
        return reponse;
    }
}

