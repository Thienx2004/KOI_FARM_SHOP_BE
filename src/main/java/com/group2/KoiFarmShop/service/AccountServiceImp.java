package com.group2.KoiFarmShop.service;


import com.group2.KoiFarmShop.dto.LoginRequest;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;

public interface AccountServiceImp {


    public Account createAccount(AccountCreationDTO accountCreationDTO);
    public Account getAccount(int id);
    public String login(LoginRequest loginRequest);
}
