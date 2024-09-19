package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.LoginRequest;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/koi")
public class LoginController {

    @Autowired
    private AccountServiceImp accountServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String result = accountServiceImp.login(loginRequest);
        return ResponseEntity.ok(result);
    }
}

