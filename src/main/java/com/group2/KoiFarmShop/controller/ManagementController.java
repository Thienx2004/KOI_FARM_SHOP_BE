package com.group2.KoiFarmShop.controller;

import com.google.protobuf.Api;
import com.group2.KoiFarmShop.dto.AccountDTO;
import com.group2.KoiFarmShop.dto.request.AccountCreateRequest;
import com.group2.KoiFarmShop.dto.request.AccountUpdateStatusRequest;
import com.group2.KoiFarmShop.dto.response.AccountCreateRespone;
import com.group2.KoiFarmShop.dto.response.AccountPageRespone;
import com.group2.KoiFarmShop.dto.response.ApiReponse;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.Role;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("manage")
public class ManagementController {

    @Autowired
    private AccountService accountService;


    @GetMapping("/allAcount")
    @Operation(summary = "Lấy toàn bộ tài khoản", description = "")

    public ApiReponse<AccountPageRespone> getAllAccounts(@RequestParam int page, @RequestParam int pageSize) {
        if(page<=0||pageSize<=0){
            throw new AppException(ErrorCode.INVALIDNUMBER);
        }
        AccountPageRespone accountPageRespone = accountService.getAllAccounts(page, pageSize);
        return ApiReponse.<AccountPageRespone>builder().data(accountPageRespone).statusCode(200).build();
    }

    @PutMapping("/updateStatus/{id}")
    @Operation(summary = "Cập nhật trạng thái tài khoản", description = "")

    public ApiReponse<AccountDTO> changeStatus(@PathVariable int id) {
        AccountDTO accountDTO = accountService.updateAccountStatus(id);
        return ApiReponse.<AccountDTO>builder().data(accountDTO).statusCode(200).build();
    }
    @PostMapping("/createAccount")
    @Operation(summary = "Tạo tài khoản", description = "")

    public ApiReponse<AccountCreateRespone> createAccount (@RequestBody AccountCreateRequest request ) {
        AccountCreateRespone accountCreateRespone = accountService.createAccount(request);

    return ApiReponse.<AccountCreateRespone>builder().data(accountCreateRespone).statusCode(200).build() ;
}

    @GetMapping("/search")
    @Operation(summary = "Tìm tài khoản theo email",description = "")
    public ApiReponse<AccountDTO> getAccountByEmail(@RequestParam String email) {
        AccountDTO accountDTO = accountService.searchByEmail(email);
        return ApiReponse.<AccountDTO>builder().data(accountDTO).statusCode(200).build();
    }

}
