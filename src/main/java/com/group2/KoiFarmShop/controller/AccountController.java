package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.service.AccountService;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImp accountServiceImp;

    @PostMapping("/register")
    public ApiReponse<Account> createAccount(@RequestBody AccountCreationDTO accountCreationDTO) {

        ApiReponse<Account> reponse = new ApiReponse<>();
        reponse.setData(accountServiceImp.createAccount(accountCreationDTO));
        return reponse;
    }
}
