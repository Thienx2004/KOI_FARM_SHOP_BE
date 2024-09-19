package com.koifarm.koifarmshop.controller;

import com.koifarm.koifarmshop.model.LoginRequest;
import com.koifarm.koifarmshop.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/koi")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String result = loginService.login(loginRequest);
        return ResponseEntity.ok(result);
    }
}

