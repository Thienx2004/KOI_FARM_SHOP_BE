package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishPageResponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.CategoryRepository;
import com.group2.KoiFarmShop.service.KoiFishServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/koifish")
public class KoiFishController {
    @Autowired
    KoiFishServiceImp koiFishService;
    @Autowired
    CategoryRepository categoryRepository;
    // Lấy toàn bộ danh sách cá Koi
    @GetMapping("/allkoi")
    @Operation(summary = "Lấy danh sách Koi", description = "-Nguyễn Hoàng Thiên")
    public ApiReponse<KoiFishPageResponse> getAllKoiFish(@RequestParam("page") int page,@RequestParam("pageSize") int pageSize) {
        KoiFishPageResponse koiFishList = koiFishService.getAllKoiFish(page,pageSize);
        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishList).statusCode(200).build();
    }

    // Lấy cá Koi theo Category và Trang
    @GetMapping("/category")
    @Operation(summary = "Lấy danh sách Koi theo Category", description = "-Nguyễn Hoàng Thiên")
    public ApiReponse<KoiFishPageResponse> getKoiByCategory(@RequestParam("categoryId") int id, @RequestParam("page") int page,@RequestParam("pageSize") int pageSize) {
        Category category=categoryRepository.findByCategoryID(id);
        KoiFishPageResponse koiFishList = koiFishService.getKoiByCategory(category, page,pageSize);
        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishList).statusCode(200).build();
    }

    // Lấy cá Koi theo ID
    @GetMapping("/{id}")
    @Operation(summary = "Lấy Koi theo id", description = "-Nguyễn Hoàng Thiên")
    public ApiReponse<KoiFishReponse> getKoiFishById(@PathVariable int id) {
        KoiFishReponse koiFish = koiFishService.getKoiFishById(id);

            return ApiReponse.<KoiFishReponse>builder().data(koiFish).build();
    }

    // Thêm mới cá Koi
    @PostMapping("/add")
    @Operation(summary = "Thêm cá Koi", description = "-Nguyễn Hoàng Thiên")
    public ApiReponse<KoiFishReponse> addKoiFish(@RequestBody KoiRequest koiFish) {
        if(koiFish.getCategoryId()==0){
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
        KoiFishReponse koiFishReponse=koiFishService.addKoiFish(koiFish);
        return ApiReponse.<KoiFishReponse>builder().data(koiFishReponse).message("Thêm Koi thành công").statusCode(200).build();
    }


    // Cập nhật cá Koi
    @PutMapping("/update/{id}")
    @Operation(summary = "Cập nhật Koi theo id", description = "-Nguyễn Hoàng Thiên")
    public ApiReponse<KoiFishReponse> updateKoiFish(@PathVariable int id, @RequestBody KoiRequest koiFish) {
        if(koiFish.getCategoryId()==0){
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
        KoiFishReponse koiFishReponse = koiFishService.updateKoiFish(koiFish,id);
        return ApiReponse.<KoiFishReponse>builder().data(koiFishReponse).message("Cập nhật thành công").statusCode(200).build();
    }

    @GetMapping("/filter")
    @Operation(summary = "Lọc cá Koi", description = "-Nguyễn Hoàng Thiên")
    public ApiReponse<KoiFishPageResponse> filterKoiFish(
            @RequestParam(required = false) String categoryID,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String minSize,
            @RequestParam(required = false) String maxSize,
            @RequestParam(required = false) String age,
            @RequestParam(required = false) String minPrice,
            @RequestParam(required = false) String maxPrice,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String sortField1,
            @RequestParam(required = false) String sortDirection1,
            @RequestParam(required = false) String sortField2,
            @RequestParam(required = false) String sortDirection2,
            @RequestParam(defaultValue = "1") int page,      // Thêm tham số page với giá trị mặc định là 1
            @RequestParam(defaultValue = "6") int pageSize
            // Thêm tham số pageSize với giá trị mặc định là 6
    ) {

        KoiFishPageResponse koiFishReponses = koiFishService.filterKoiFish(categoryID,maxSize,minSize,gender, age, minPrice, maxPrice, origin, page, pageSize,sortField1,sortDirection1,sortField2,sortDirection2); // Truyền thêm page và pageSize vào filter
        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishReponses).build();
    }

    @GetMapping("/compare")
    @Operation(summary = "So sánh Koi", description = "-Nguyễn Hoàng Thiên")
    public ApiReponse<List<KoiFishReponse>> compareKoiFish(@RequestParam("koiFishId1") int id1, @RequestParam("koiFishId2") int id2) {
        List<KoiFishReponse> koiFishReponses = koiFishService.compareKoiFish(id1, id2);
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishReponses).build();
    }
}
