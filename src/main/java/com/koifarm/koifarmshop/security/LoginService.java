package com.koifarm.koifarmshop.security;

import com.koifarm.koifarmshop.entity.Account;
import com.koifarm.koifarmshop.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String login(LoginRequest loginRequest) {
        // Tìm kiếm tài khoản dựa trên email
        Optional<Account> optionalAccount = accountRepository.findByEmail(loginRequest.getEmail());

        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Kiểm tra mật khẩu
            if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
                // Kiểm tra trạng thái xác thực
                if (!account.getIsVerify()) {
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
}

