package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.Content;
import com.group2.KoiFarmShop.dto.request.LoginGoogleRequest;
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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.Optional;
import java.util.UUID;

import java.util.*;


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
    public ApiReponse logingg(LoginGoogleRequest loginGoogleRequest) {
        ApiReponse apiReponse = new ApiReponse();

        try {
            // Tìm kiếm tài khoản dựa trên email
            Optional<Account> optionalAccount = accountRepository.findByEmail(loginGoogleRequest.getEmail());
            Account account;

            if (optionalAccount.isPresent()) {
                account = optionalAccount.get();
            } else {
                // Tạo tài khoản mới
                AccountCreationDTO accountCreationDTO = new AccountCreationDTO();
                accountCreationDTO.setEmail(loginGoogleRequest.getEmail());
                accountCreationDTO.setFullName(loginGoogleRequest.getName());
                accountCreationDTO.setVerified(true);
                account = createAccount(accountCreationDTO);
            }
            // Kiểm tra trạng thái xác thực
            if (!account.isVerified()) {
                account.setVerified(true);
                accountRepository.updateVerify(loginGoogleRequest.getEmail(),true);
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

        if (accountRepository.existsByEmail(accountCreationDTO.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        Role role = new Role();
        role.setRoleID(3);

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
        // Tạo mã OTP
        String otp = generateOTP();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(otp); // Lưu OTP trong trường token
        verificationToken.setAccount(account);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(10)); // OTP hết hạn sau 10 phút

        verificationTokenRepository.save(verificationToken);

        // Gửi OTP qua email
        emailService.sendVerificationEmail(accountCreationDTO.getEmail(), otp);
        }





        return account;
    }

    @Override
    public Account getAccount(int id) {
        return null;
    }

    @Override
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Tạo mã OTP 6 chữ số
        return String.valueOf(otp);
    }


    @Override
    public ApiReponse<String> verifyOTP(String email, String otp) {
        ApiReponse apiReponse = new ApiReponse();
        // Tìm tài khoản qua email
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));

        // Tìm mã OTP trong bảng VerificationToken
        VerificationToken verificationToken = verificationTokenRepository.findByToken(otp)
                .orElseThrow(() -> new AppException(ErrorCode.INVALIDOTP));

        // Kiểm tra xem OTP có hết hạn không
        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.OTP_EXPIRED);
        }

        // Xác thực tài khoản
        account.setVerified(true);
        accountRepository.save(account);
        apiReponse.setData("Xác thực tài khoản thành công!");
        return apiReponse;
    }


}
