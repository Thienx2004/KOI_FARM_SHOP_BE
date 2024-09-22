package com.group2.KoiFarmShop.service;


import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;

public interface AccountServiceImp {


    public Account createAccount(AccountCreationDTO accountCreationDTO);
    public Account getAccount(int id);
    public ApiReponse login(LoginRequest loginRequest);

    public ApiReponse logingg(String email);

    public String generateOTP();
    public ApiReponse<String> resendOTP(String email);
    ApiReponse<String> verifyOTP(String email, String otp);

}
