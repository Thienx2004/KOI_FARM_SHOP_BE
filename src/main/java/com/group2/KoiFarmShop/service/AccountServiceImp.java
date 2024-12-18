package com.group2.KoiFarmShop.service;


import com.group2.KoiFarmShop.dto.response.ApiReponse;
import com.group2.KoiFarmShop.dto.request.LoginGoogleRequest;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;
import jakarta.mail.MessagingException;

public interface AccountServiceImp {


    public Account createAccount(AccountCreationDTO accountCreationDTO) throws MessagingException;
    public ApiReponse login(LoginRequest loginRequest);

    public ApiReponse logingg(LoginGoogleRequest loginGoogleRequest);

    public String generateOTP();
    public ApiReponse<String> resendOTP(String email) throws MessagingException;
    ApiReponse<String> verifyOTP(String email, String otp);
    public String checkRole(String token);

}
