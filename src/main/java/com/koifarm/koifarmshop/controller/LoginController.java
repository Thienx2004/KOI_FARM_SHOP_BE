package com.koifarm.koifarmshop.controller;

import com.koifarm.koifarmshop.model.LoginRequest;
import com.koifarm.koifarmshop.security.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("test");
        System.out.println(loginRequest.getEmail());
        String result = loginService.login(loginRequest);
        return ResponseEntity.ok(result);
    }
}
