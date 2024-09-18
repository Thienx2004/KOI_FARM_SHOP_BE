package com.koifarm.koifarmshop.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;

}
