package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.Role;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountServiceImp{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(AccountCreationDTO accountCreationDTO) {

        if(accountRepository.existsByEmail(accountCreationDTO.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

        if(!accountCreationDTO.getPassword().equals(accountCreationDTO.getConfirmPassword()))
            throw new AppException(ErrorCode.CONFIRMPASSWORD_INVALID);

        Role role = new Role();
        role.setRoleID(3);

        Account account = new Account();
        account.setEmail(accountCreationDTO.getEmail());
        account.setFullName(accountCreationDTO.getFullName());
        account.setRole(role);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        account.setPassword(passwordEncoder.encode(accountCreationDTO.getPassword()));

        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(int id) {
        return null;
    }
}
