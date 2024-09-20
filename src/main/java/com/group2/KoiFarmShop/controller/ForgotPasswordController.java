package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.MailBody;
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
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));

        int otp =otpGen();

        MailBody mailBody=MailBody.builder()
                .to(email)
                .text("This is OTP" + otp)
                .subject("OTP for forgot password")
                .build();
        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() +70 *1000))
                .account(account)
                .build();
        emailService.sendSimpleMess(mailBody);
        forgotPasswordRepository.save(fp);
        return ResponseEntity.ok("Email send");
    }

    @PostMapping("/otp/{otp}/{email}")
    public ResponseEntity<String> verifyOTP(@PathVariable Integer otp, @PathVariable String email) {
        Account account = accountRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));

        ForgotPassword fp = forgotPasswordRepository.findByOptAndAccount(otp, account).orElseThrow(() -> new AppException(ErrorCode.INVALIDOTP));

        if(fp.getExpirationTime().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP expired", HttpStatus.EXPECTATION_FAILED);
        }
        return ResponseEntity.ok("OTP verified");
    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword,
                                                        @PathVariable String email) {
        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return new ResponseEntity<>("Please enter the password again!", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        accountRepository.updatePassword(email, encodedPassword);

        return ResponseEntity.ok("Password has been changed!");
    }

    private Integer otpGen(){
        Random rand = new Random();
        return rand.nextInt(100_000, 999_999);
    }
}
