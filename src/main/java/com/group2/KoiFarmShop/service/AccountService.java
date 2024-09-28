package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.Content;
import com.group2.KoiFarmShop.dto.reponse.ProfileRespone;
import com.group2.KoiFarmShop.dto.request.LoginGoogleRequest;
import com.group2.KoiFarmShop.dto.request.LoginRequest;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.dto.request.ProfileRequest;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.Role;
import com.group2.KoiFarmShop.entity.VerificationToken;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.repository.ForgotPasswordRepositoryI;
import com.group2.KoiFarmShop.repository.VerificationTokenRepository;
import com.group2.KoiFarmShop.ultils.JWTUltilsHelper;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.Optional;
import java.util.*;

import static java.lang.System.out;


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
    @Autowired
    private ForgotPasswordRepositoryI forgotPasswordRepository;

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
                    content.setId(account.getAccountID());
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
                Role role = new Role();
                role.setRoleID(3);
                account = new Account();
                account.setEmail(loginGoogleRequest.getEmail());
                account.setFullName(loginGoogleRequest.getName());
                account.setRole(role);
                accountRepository.save(account);
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
            content.setId(account.getAccountID());
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

        if(!account.isVerified()) {
        // Tạo mã OTP
        String otp = generateOTP();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(otp); // Lưu OTP trong trường token
        verificationToken.setAccount(account);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(2)); // OTP hết hạn sau 2 phút

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

//    @Override
//    public Account getAccount(int id) {
//        return null;
//    }

    @Override
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Tạo mã OTP 6 chữ số
        return String.valueOf(otp);
    }

    @Transactional
    @Override
    public ApiReponse<String> resendOTP(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));

        if (account.isVerified()) {
            throw new AppException(ErrorCode.ACCOUNT_ALREADY_VERIFIED);
        }

//        VerificationToken verificationToken1 = verificationTokenRepository.findByAccount_AccountID(account.getAccountID())
//                .orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));
//        System.out.println(verificationToken1.getToken());
//
//        // Xóa mã OTP cũ (nếu có)
//        verificationTokenRepository.deleteById(verificationToken1.getId());

        // Tạo mã OTP mới
        String newOtp = generateOTP();

        // Tạo đối tượng VerificationToken mới
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(newOtp);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(10)); // Hết hạn sau 10 phút
        verificationToken.setAccount(account);

        // Lưu mã OTP mới vào cơ sở dữ liệu
        verificationTokenRepository.save(verificationToken);

        // Gửi OTP mới qua email
        emailService.sendVerificationEmail(account.getEmail(), newOtp);
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData("OTP đã gửi thành công!");
        return apiReponse;
    }


    @Override
    public ApiReponse<String> verifyOTP(String email, String otp) {
        ApiReponse apiReponse = new ApiReponse();

        // Tìm tài khoản qua email
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));

        if(account.isVerified()) {
            List<VerificationToken> verificationTokenList = verificationTokenRepository.findByAccount_AccountID(account.getAccountID());
            if(!verificationTokenList.isEmpty()) {
                VerificationToken tmp = new VerificationToken();
                for(VerificationToken verificationToken : verificationTokenList) {
                    if(verificationToken.getToken().equals(otp)) {
                        tmp = verificationToken;
                        apiReponse.setMessage("OTP hợp lệ");
                        String tokenOTP = jwtUltilsHelper.generateTokenForOTP(account);
                        apiReponse.setData(tokenOTP);

                    }
                }
                if(tmp.getToken() != null) {
                    for (VerificationToken verificationToken : verificationTokenList) {
                        verificationTokenRepository.delete(verificationToken);
                    }
                } else throw new AppException(ErrorCode.INVALIDOTP);
            }

        }
        else {
            // Tìm mã OTP trong bảng VerificationToken
            VerificationToken verificationToken = verificationTokenRepository.findByToken(otp)
                    .orElseThrow(() -> new AppException(ErrorCode.INVALIDOTP));
            out.println(verificationToken.getId());
            out.println(verificationToken.getToken());

            if (!verificationToken.getToken().equals(otp))
                throw new AppException(ErrorCode.INVALIDOTP);


            // Kiểm tra xem OTP có hết hạn không
            if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                throw new AppException(ErrorCode.OTP_EXPIRED);
            }


            // Xác thực tài khoản
            account.setVerified(true);

            accountRepository.save(account);
            apiReponse.setMessage("Xác thực tài khoản thành công!");
        }
        return apiReponse;
    }

   public ProfileRespone getProfile (String email) {
        Optional<Account> account = accountRepository.findByEmail(email);
        return ProfileRespone.builder()
                .id(account.get().getAccountID())
                .password((account.get().getPassword()))
                .fullname(account.get().getFullName())
                .email(account.get().getEmail())
                .phone(account.get().getPhone())
                .address(account.get().getAddress())
                .isVerified(account.get().isVerified())
                .build();
   }


    public ProfileRespone updateProfile (ProfileRequest profileRequest,int id) {
        Account account = new Account();
        Optional<Account>account1 = accountRepository.findById(id);
        account.setAccountID(id);
        account.setEmail(account1.get().getEmail());
        account.setFullName(profileRequest.getFullName());
        account.setPassword(profileRequest.getPassword());
        account.setAddress(profileRequest.getAddress());
        account.setPhone(profileRequest.getPhone());
        account.setVerified(account1.get().isVerified());
        account.setRole(account1.get().getRole());

        Account accSave = accountRepository.save(account);
        return ProfileRespone.builder()
                .id(accSave.getAccountID())
                .password(passwordEncoder.encode(accSave.getPassword()))
                .fullname(accSave.getFullName())
                .email(accSave.getEmail())
                .phone(accSave.getPhone())
                .address(accSave.getAddress())
                .isVerified(accSave.isVerified())
                .build();
    }


}
