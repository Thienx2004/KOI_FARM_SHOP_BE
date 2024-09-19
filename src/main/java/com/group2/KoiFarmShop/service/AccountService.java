package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.LoginRequest;
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

import java.util.Optional;

@Service
public class AccountService implements AccountServiceImp{

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String login(LoginRequest loginRequest) {
        // Tìm kiếm tài khoản dựa trên email
        Optional<Account> optionalAccount = accountRepository.findByEmail(loginRequest.getEmail());

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Kiểm tra mật khẩu
            if (loginRequest.getPassword().matches(account.getPassword())) {
                // Kiểm tra trạng thái xác thực
                if (!account.isVerified()) {
                    return "Account not verified. Please verify your email.";
                }

                // Tài khoản đăng nhập thành công
                return "Login successful!";
            } else {
                return "Invalid password.";
            }
        }

        // Email không tồn tại
        return "Account not found.";
    }

    @Override
    public Account createAccount(AccountCreationDTO accountCreationDTO) {

        if(accountRepository.existsByEmail(accountCreationDTO.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);
        
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
