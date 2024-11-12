package com.group2.KoiFarmShop.controller;

import com.google.protobuf.Api;
import com.group2.KoiFarmShop.dto.AccountDTO;
import com.group2.KoiFarmShop.dto.request.AccountCreateRequest;
import com.group2.KoiFarmShop.dto.request.AccountUpdateStatusRequest;
import com.group2.KoiFarmShop.dto.request.CreateCategoryRequest;
import com.group2.KoiFarmShop.dto.response.*;
import com.group2.KoiFarmShop.entity.*;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.http.HttpClient;
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
    @Autowired
    KoiFishService koiFishService;
    @Autowired
    BatchService batchService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private ConsignmentService consignmentService;


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

    public ApiReponse<AccountDTO> changeStatus(@PathVariable int id, HttpServletRequest request) {
//        String token = authenticationService.extractTokenFromRequest(request);
//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));
//        String emailFromToken = authenticationService.validateTokenByEmail(token);
//        if (account.getEmail().equals(emailFromToken)) {
//            throw new AppException(ErrorCode.POWERLESS);
//        }
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

        PaginReponse<OrderHistoryReponse> historyReponsePaginReponse = orderService.getOrdersHistory(pageNo, pageSize, null);
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
    @Operation(summary = "Cập nhật trạng thái đơn hàng", description = "")
    public ApiReponse changeStatusOrder(@RequestParam int orderID, @RequestParam int status) {
        orderService.changeStatus(orderID, status);
        return ApiReponse.<OrderHistoryReponse>builder().message("Cập nhật thành công").statusCode(200).build();
    }

    @GetMapping("/searchCategory")
    @Operation(summary = "Tìm kím category theo tên", description = "")
    public ApiReponse<CategoryPageResponse> searchCategoryByName(@RequestParam String name, @RequestParam int pageNum, @RequestParam int pageSize) {
        CategoryPageResponse categoryReponseList = categoryService.searchCategoryByName(name, pageNum, pageSize);
        ApiReponse apiReponse = new ApiReponse();
        apiReponse.setData(categoryReponseList);
        return apiReponse;
    }

    @GetMapping("/searchOrder")
    @Operation(summary = "Tìm kím order theo transaction code", description = "")

    public ApiReponse<PaginReponse<OrderHistoryReponse>> searchOrdersByTransactionCode(@RequestParam String transactionCode, int pageNum, @RequestParam int pageSize) {
        PaginReponse<OrderHistoryReponse> reponse = orderService.getPaginReponse(transactionCode, pageNum, pageSize);
        ApiReponse<PaginReponse<OrderHistoryReponse>> historyReponse = new ApiReponse<>();
        historyReponse.setData(reponse);

        return historyReponse;
    }
    @GetMapping("/searchFish")
    @Operation(summary = "Tìm kím cá", description = "Tính cách, age, category, size(bé hơn hoặc bằng), gender")

    public ApiReponse<KoiFishPageResponse> searchKoi(@RequestParam String keyword, @RequestParam int pageNum, @RequestParam int pageSize ) {
        KoiFishPageResponse resp = koiFishService.searchKoiFish(keyword, pageNum, pageSize);
        return ApiReponse.<KoiFishPageResponse>builder().data(resp).statusCode(200).build();
    }
    @GetMapping("/searchBatch")
    @Operation(summary = "Tìm kiếm lô", description ="age, size, category, quantity (bé hơn hoặc bằng)")
    public ApiReponse<BatchPageReponse> getBatchByCategory(@RequestParam String keyword, @RequestParam int pageNum, @RequestParam int pageSize) {
        ApiReponse apiReponse = new ApiReponse();
        BatchPageReponse batchPageReponse = batchService.searchBatch(keyword, pageNum, pageSize);
        apiReponse.setData(batchPageReponse);
        return apiReponse;
    }

    @GetMapping("/searchKoiByHealthCare")
    @Operation(summary = "Tìm kiếm Koi theo health care", description ="healthStatus,note")

    public ApiReponse<KoiFishPageResponse> searchKoiByHealthCare(@RequestParam String keyword, @RequestParam int pageNum, @RequestParam int pageSize){
        KoiFishPageResponse resp = koiFishService.searchKoiFishByHealthCare(keyword, pageNum, pageSize);
        return ApiReponse.<KoiFishPageResponse>builder().data(resp).build();
    }
}
