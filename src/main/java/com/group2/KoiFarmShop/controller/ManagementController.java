package com.group2.KoiFarmShop.controller;

import com.google.protobuf.Api;
import com.group2.KoiFarmShop.dto.AccountDTO;
import com.group2.KoiFarmShop.dto.request.AccountCreateRequest;
import com.group2.KoiFarmShop.dto.request.AccountUpdateStatusRequest;
import com.group2.KoiFarmShop.dto.request.CreateCategoryRequest;
import com.group2.KoiFarmShop.dto.response.*;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.Orders;
import com.group2.KoiFarmShop.entity.Role;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.service.AccountService;
import com.group2.KoiFarmShop.service.CategoryService;
import com.group2.KoiFarmShop.service.CategoryServiceImp;
import com.group2.KoiFarmShop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("manage")
public class ManagementController {

    @Autowired
    private AccountService accountService;
    @Autowired
    CategoryServiceImp categoryServiceImp;
    @Autowired
    CategoryService categoryService;
    @Autowired
    OrderService orderService;


    @GetMapping("/allAcount")
    @Operation(summary = "Lấy toàn bộ tài khoản", description = "")

    public ApiReponse<AccountPageRespone> getAllAccounts(@RequestParam int page, @RequestParam int pageSize) {
        if (page <= 0 || pageSize <= 0) {
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

    public ApiReponse<AccountCreateRespone> createAccount(@RequestBody AccountCreateRequest request) {
        AccountCreateRespone accountCreateRespone = accountService.createAccount(request);

        return ApiReponse.<AccountCreateRespone>builder().data(accountCreateRespone).statusCode(200).build();
    }

//    @GetMapping("/search")
//    @Operation(summary = "Tìm tài khoản theo email chính xác", description = "")
//    public ApiReponse<AccountDTO> getAccountByEmail(@RequestParam String email) {
//        AccountDTO accountDTO = accountService.searchByEmail(email);
//        return ApiReponse.<AccountDTO>builder().data(accountDTO).statusCode(200).build();
//    }

    @GetMapping("/getAllCategory")
    @Operation(summary = "Lấy toàn bộ category", description = "")
    public ApiReponse<CategoryPageResponse> getListAllCate(@RequestParam int pageNum, @RequestParam int pageSize) {

        CategoryPageResponse categoryReponseList = categoryService.getAllCategories(pageNum, pageSize);
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(categoryReponseList);
        return apiReponse;
    }

    @PostMapping("/addCategory")
    @Operation(summary = "Thêm category", description = "")
    public ApiReponse<CreateCategoryRespone> createCategory(@RequestParam String cateName, @RequestParam String description, @RequestParam boolean status, @RequestParam MultipartFile file) throws IOException {
        CreateCategoryRespone createCategoryRespone = categoryService.addCategory(cateName, description, status, file);
        return ApiReponse.<CreateCategoryRespone>builder().data(createCategoryRespone).statusCode(200).build();
    }

    @PutMapping("/changeStatus/{id}")
    @Operation(summary = "Cập nhật status cho category", description = "")
    public ApiReponse<CategoryReponse> updateStatus(@PathVariable int id) {
        CategoryReponse categoryReponse = categoryService.updateStatus(id);
        return ApiReponse.<CategoryReponse>builder().data(categoryReponse).statusCode(200).build();
    }

    @PutMapping("/updateCategory/{id}")
    @Operation(summary = "Cập nhật category", description = "")
    public ApiReponse<CreateCategoryRespone> updateCategory(@ModelAttribute CreateCategoryRequest createCategoryRequest, @PathVariable int id) throws IOException {
//        System.out.println(createCategoryRequest.getImgFile());
        CreateCategoryRespone respone = categoryService.updateCategory(id, createCategoryRequest);
        return ApiReponse.<CreateCategoryRespone>builder().data(respone).statusCode(200).build();
    }


    @GetMapping("/searchEmail")
    @Operation(summary = "Tìm khách hàng theo email ", description = "")
    public ApiReponse<AccountPageRespone> searchAccountsByEmail(@RequestParam String email,
                                                  @RequestParam int page,
                                                  @RequestParam int pageSize) {
        if (page <= 0 || pageSize <= 0) {
            throw new AppException(ErrorCode.INVALIDNUMBER);
        }
        AccountPageRespone accountPageRespone = accountService.searchAccountByEmail(email, page, pageSize);
        return ApiReponse.<AccountPageRespone>builder().data(accountPageRespone).statusCode(200).build();

    }
    @GetMapping("/getAllOrders")
    @Operation(summary = "Lấy toàn bộ đơn hàng ", description = "")

    public ApiReponse<PaginReponse<OrderHistoryReponse>> getAllOrdersWithDetails(
//            @RequestParam(required = false, defaultValue = "") String accountID,
            //@RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "3") int pageSize) {

        PaginReponse<OrderHistoryReponse> historyReponsePaginReponse = orderService.getOrdersHistory(pageNo, pageSize,null);
        ApiReponse<PaginReponse<OrderHistoryReponse>> historyReponse = new ApiReponse<>();
        historyReponse.setData(historyReponsePaginReponse);

        return historyReponse;
    }
    @GetMapping("/getOrderDetail")
    @Operation(summary = "Lấy order detail theo order id", description = "")

    public ApiReponse<List<OrderDetailReponse>> getOrderDetail(@RequestParam int orderID) {

        ApiReponse<List<OrderDetailReponse>> resp = new ApiReponse<>();
        resp.setData(orderService.getOrderDetail(orderID));

        return resp;
    }

    @PostMapping("/changeStatus")
    @Operation(summary = "Cập nhật trạng thái đơn hàng",description = "")
    public ApiReponse changeStatusOrder(@RequestParam int orderID, @RequestParam int status) {
        orderService.changeStatus(orderID, status);
        return ApiReponse.<OrderHistoryReponse>builder().message("Cập nhật thành công").statusCode(200).build();
    }

    @GetMapping("/searchCategory")
    @Operation(summary = "Tìm kím category theo tên",description = "")
    public ApiReponse<List<CreateCategoryRespone>> searchCategoryByName(@RequestParam String name){
        List<CreateCategoryRespone> createCategoryRespone = categoryService.searchCategoryByName(name);
        ApiReponse<List<CreateCategoryRespone>> resp = new ApiReponse<>();
        resp.setData(createCategoryRespone);
        resp.setStatusCode(200);
        return resp;
    }

    @GetMapping("/searchOrder")
    @Operation(summary = "Tìm kím order theo transaction code",description = "")

    public ApiReponse<OrderHistoryReponse> searchOrdersByTransactionCode(@RequestParam String transactionCode){
        OrderHistoryReponse reponse=orderService.getOrderDetailsByTransactionCode(transactionCode);
        return ApiReponse.<OrderHistoryReponse>builder().data(reponse).build();
    }




}
