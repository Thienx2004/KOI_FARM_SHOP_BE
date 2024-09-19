package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.Content;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.Role;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.ultils.JWTUltilsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements AccountServiceImp{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUltilsHelper jwtUltilsHelper;
    @Override
    public ApiReponse login(LoginRequest loginRequest) {
        // Tìm kiếm tài khoản dựa trên email
        Optional<Account> optionalAccount = accountRepository.findByEmail(loginRequest.getEmail());
        ApiReponse apiReponse = new ApiReponse();
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            // Kiểm tra mật khẩu
            if (passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
                // Kiểm tra trạng thái xác thực
            String Token=jwtUltilsHelper.generateToken(account.getEmail());
                Content content = new Content();
                content.setEmail(account.getEmail());
                content.setPhone(account.getPhone());
                content.setFullName(account.getFullName());
                content.setRole(account.getRole().getRoleName());
                content.setPhone(account.getPhone());
                content.setAccessToken(Token);
                apiReponse.setContent(content);
                apiReponse.setMessage("Đăng nhập thành công");
                // Tài khoản đăng nhập thành công
            } else {
                throw new AppException(ErrorCode.WRONGPASSWORD);
            }
        }else{
            throw new AppException(ErrorCode.INVALIDACCOUNT);
        }

        return apiReponse;
    }

    @Override
    public Account createAccount(AccountCreationDTO accountCreationDTO) {

        if(accountRepository.existsByEmail(accountCreationDTO.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);
        
        Role role = new Role();
        role.setRoleID(1);

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
