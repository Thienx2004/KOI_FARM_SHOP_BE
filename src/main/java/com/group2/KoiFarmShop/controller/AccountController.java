package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.AccountReponse;
import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.ProfileRespone;
import com.group2.KoiFarmShop.dto.request.AccountCreationDTO;
import com.group2.KoiFarmShop.dto.request.ProfileRequest;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.service.AccountService;
import com.group2.KoiFarmShop.service.AccountServiceImp;
import com.group2.KoiFarmShop.service.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
//@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImp accountServiceImp;
    @Autowired
    private FileServiceImp fileServiceImp;
    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ApiReponse<AccountReponse> createAccount(@RequestBody AccountCreationDTO accountCreationDTO) {

        ApiReponse<AccountReponse> reponse = new ApiReponse<>();
        Account account = new Account();
        account = accountServiceImp.createAccount(accountCreationDTO);
        AccountReponse accountReponse = new AccountReponse();
        accountReponse.setFullName(account.getFullName());
        accountReponse.setEmail(account.getEmail());
        accountReponse.setPassword(account.getPassword());
        reponse.setMessage("Đăng kí thành công! Kiểm tra email của bạn để xác thực.");


        reponse.setData(accountReponse);
        return reponse;
//            accountServiceImp.createAccount(accountCreationDTO);
//            ApiReponse apiReponse = new ApiReponse();
            //return reponse;
    }

    @PostMapping("/savefile")
    public ApiReponse<Boolean> saveFile(@RequestParam MultipartFile file) {
        ApiReponse<Boolean> reponse = new ApiReponse<>();
        boolean success = fileServiceImp.saveFile(file);
        reponse.setData(success);
        return reponse;
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        Resource resource = fileServiceImp.loadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("profile/{id}")
    ApiReponse<ProfileRespone> getProfile(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long currentAccountId = jwt.getClaim("accountId");

        // Kiểm tra xem accountID trong JWT có trùng với accountID được yêu cầu cập nhật không
        if (!currentAccountId.equals(id)) {
             throw new AppException(ErrorCode.KOINOTFOUND);
        }

        ProfileRespone profileRespone = accountService.getProfile(id);
        if (profileRespone != null) {
            return ApiReponse.<ProfileRespone>builder().data(profileRespone).build();
        }else {
            throw new AppException(ErrorCode.INVALIDACCOUNT);
        }

    }
    @PutMapping("/profile/update/{id}")
    public ApiReponse<ProfileRespone> updateProfile(@RequestBody ProfileRequest profileRequest,
                                                    @PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        Long currentAccountId = jwt.getClaim("accountId");

        // Kiểm tra xem accountID trong JWT có trùng với accountID được yêu cầu cập nhật không
        if (!currentAccountId.equals(id)) {
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
        ProfileRespone profileRespone=accountService.updateProfile(profileRequest,id);
        return ApiReponse.<ProfileRespone>builder().data(profileRespone).statusCode(200).build();
    }

}
