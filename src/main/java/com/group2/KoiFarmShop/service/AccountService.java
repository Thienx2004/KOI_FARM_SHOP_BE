package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.Content;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.Role;
import com.group2.KoiFarmShop.entity.VerificationToken;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.repository.VerificationTokenRepository;
import com.group2.KoiFarmShop.ultils.JWTUltilsHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements AccountServiceImp{

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUltilsHelper jwtUltilsHelper;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private EmailService emailService;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

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
                if(account.isVerified()){
                    String Token=jwtUltilsHelper.generateToken(account);
                    Content content = new Content();
                    content.setEmail(account.getEmail());
                    content.setPhone(account.getPhone());
                    content.setFullName(account.getFullName());
                    content.setRole(account.getRole().getRoleName());
                    content.setPhone(account.getPhone());
                    content.setAccessToken(Token);
                    apiReponse.setData(content);
                    apiReponse.setMessage("Đăng nhập thành công");
                    // Tài khoản đăng nhập thành công
                }else {
                    throw new AppException(ErrorCode.NOTVERIFYACCOUNT);
                }
            } else {
                throw new AppException(ErrorCode.WRONGPASSWORD);
            }
        }else{
            throw new AppException(ErrorCode.INVALIDACCOUNT);
        }

        return apiReponse;
    }

    @Override
    public ApiReponse logingg(String token) {
        ApiReponse apiReponse = new ApiReponse();

        try {
            Claims claims = Jwts.parser().build().parseClaimsJws(token).getBody();

            // Tìm kiếm tài khoản dựa trên email
            Optional<Account> optionalAccount = accountRepository.findByEmail(claims.get("email", String.class));
            Account account;

            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();

                // Kiểm tra trạng thái xác thực
                if (!account.isVerified()) {
                    account.setVerified(true);
                    accountRepository.updateVerify(claims.get("email", String.class),true);
                }
            } else {
                // Tạo tài khoản mới
                AccountCreationDTO accountCreationDTO = new AccountCreationDTO();
                accountCreationDTO.setEmail(claims.get("email", String.class));
                accountCreationDTO.setFullName(claims.get("name", String.class));
                accountCreationDTO.setVerified(true);
                account = createAccount(accountCreationDTO);
            }

            // Tạo token mới
            String newToken = jwtUltilsHelper.generateToken(account);

            // Cấu hình nội dung
            Content content = new Content();
            content.setEmail(account.getEmail());
            content.setPhone(account.getPhone());
            content.setFullName(account.getFullName());
            content.setRole(account.getRole().getRoleName());
            content.setAccessToken(newToken);

            apiReponse.setData(content);
            apiReponse.setMessage("Đăng nhập thành công");
        } catch (Exception e) {
            apiReponse.setMessage("Đã xảy ra lỗi: " + e.getMessage());
            apiReponse.setData(null);
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
        if(accountCreationDTO.getPassword()!=null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            account.setPassword(passwordEncoder.encode(accountCreationDTO.getPassword()));
        }
        accountRepository.save(account);
        if(!accountCreationDTO.isVerified()) {
            // Tạo mã xác thực
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setAccount(account);
            verificationToken.setExpiryDate(LocalDateTime.now().plusHours(24));

            verificationTokenRepository.save(verificationToken);

            // Gửi email xác thực
            String verificationUrl = "http://localhost:8080/koifarm/verify?token=" + token;
            emailService.sendVerificationEmail(accountCreationDTO.getEmail(), verificationUrl);
        }
        return account;
    }

    @Override
    public Account getAccount(int id) {
        return null;
    }
}
