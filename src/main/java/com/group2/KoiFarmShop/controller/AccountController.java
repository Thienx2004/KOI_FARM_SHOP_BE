package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.AccountReponse;
import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.service.AccountService;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
//@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImp accountServiceImp;

    @PostMapping("/register")
    public ApiReponse<AccountReponse> createAccount(@RequestBody AccountCreationDTO accountCreationDTO) {

        ApiReponse<AccountReponse> reponse = new ApiReponse<>();
        Account account = new Account();
        account = accountServiceImp.createAccount(accountCreationDTO);
        AccountReponse accountReponse = new AccountReponse();
        accountReponse.setFullName(account.getFullName());
        accountReponse.setEmail(account.getEmail());
        accountReponse.setPassword(account.getPassword());

        reponse.setData(accountReponse);
        return reponse;
    }
}
