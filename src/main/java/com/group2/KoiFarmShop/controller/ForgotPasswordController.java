package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.MailBody;
import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.ForgotPassword;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.repository.ForgotPasswordRepositoryI;
import com.group2.KoiFarmShop.service.EmailService;
import com.group2.KoiFarmShop.ultils.ChangePassword;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
//@RequestMapping("/login")
public class ForgotPasswordController {

    private final EmailService emailService;
    private final AccountRepository accountRepository;
    private final ForgotPasswordRepositoryI forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordController(AccountRepository accountRepository, EmailService emailService, ForgotPasswordRepositoryI forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //send mail for otp
    @PostMapping("/forgotPassword/{email}")
    public ApiReponse<String> verifyEmail(@PathVariable String email) {
        ApiReponse apiReponse = new ApiReponse();
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));

        int otp =otpGen();

        MailBody mailBody=MailBody.builder()
                .to(email)
                .text("This is OTP: " + otp)
                .subject("OTP for forgot password")
                .build();
        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() +60 *1000*5))
                .account(account)
                .build();
        emailService.sendSimpleMess(mailBody);
        forgotPasswordRepository.save(fp);
        apiReponse.setData("Email send");
        return apiReponse;
    }

    @PostMapping("/otp/{otp}/{email}")
    public  ApiReponse<String> verifyOTP(@PathVariable Integer otp, @PathVariable String email) {
        ApiReponse apiReponse = new ApiReponse();

        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));

        ForgotPassword fp = forgotPasswordRepository.findByOptAndAccount(otp, account).orElseThrow(() -> new AppException(ErrorCode.INVALIDOTP));

        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            throw new AppException(ErrorCode.INVALIDOTP);
        }
        apiReponse.setData("OTP verified");
        return apiReponse;
    }

    @PostMapping("/changePassword/{email}")
    public ApiReponse<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                    @PathVariable String email) {
        ApiReponse apiReponse = new ApiReponse();

        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
             throw new AppException(ErrorCode.PASSWORDINVALID);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        accountRepository.updatePassword(email, encodedPassword);
        apiReponse.setData("Password has been changed!");
        return apiReponse;
    }

    private Integer otpGen(){
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }
}