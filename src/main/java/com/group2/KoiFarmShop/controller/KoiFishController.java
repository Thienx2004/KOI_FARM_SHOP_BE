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
    public ApiReponse<KoiFishPageResponse> getAllKoiFish(@RequestParam("page") int page,@RequestParam("pageSize") int pageSize) {
        KoiFishPageResponse koiFishList = koiFishService.getAllKoiFish(page,pageSize);
        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishList).statusCode(200).build();
    }

    // Lấy 5 cá Koi theo Category và Trang
    @GetMapping("/category")
    public ApiReponse<KoiFishPageResponse> getKoiByCategory(@RequestParam("categoryId") int id, @RequestParam("page") int page,@RequestParam("pageSize") int pageSize) {
        Category category=categoryRepository.findByCategoryID(id);
        KoiFishPageResponse koiFishList = koiFishService.getKoiByCategory(category, page,pageSize);
        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishList).statusCode(200).build();
    }

    // Lấy cá Koi theo ID
    @GetMapping("/{id}")
    public ApiReponse<KoiFishReponse> getKoiFishById(@PathVariable int id) {
        KoiFishReponse koiFish = koiFishService.getKoiFishById(id);
        if (koiFish != null) {
            return ApiReponse.<KoiFishReponse>builder().data(koiFish).build();
        } else {
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
    }

    // Thêm mới cá Koi
    @PostMapping("/add")
    public ApiReponse<KoiFishReponse> addKoiFish(@RequestBody KoiRequest koiFish) {

        KoiFishReponse koiFishReponse=koiFishService.addKoiFish(koiFish);
        return ApiReponse.<KoiFishReponse>builder().data(koiFishReponse).message("Thêm Koi thành công").statusCode(200).build();
    }


    // Cập nhật cá Koi
    @PutMapping("/update/{id}")
    public ApiReponse<KoiFishReponse> updateKoiFish(@PathVariable int id, @RequestBody KoiRequest koiFish) {

        KoiFishReponse koiFishReponse = koiFishService.updateKoiFish(koiFish,id);
        return ApiReponse.<KoiFishReponse>builder().data(koiFishReponse).message("Cập nhật thành công").statusCode(200).build();
    }

    @GetMapping("/filter")
    public ApiReponse<KoiFishPageResponse> filterKoiFish(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") int page,      // Thêm tham số page với giá trị mặc định là 1
            @RequestParam(defaultValue = "10") int pageSize  // Thêm tham số pageSize với giá trị mặc định là 10
    ) {
        if (gender == null && age == null && minPrice == null && maxPrice == null && origin == null && status == null) {
            KoiFishPageResponse koiFishReponses = koiFishService.getAllKoiFish(page, pageSize);  // Truyền page và pageSize vào service
            return ApiReponse.<KoiFishPageResponse>builder().data(koiFishReponses).build();
        }

        KoiFishPageResponse koiFishReponses = koiFishService.filterKoiFish(gender, age, minPrice, maxPrice, origin, status, page, pageSize); // Truyền thêm page và pageSize vào filter
        return ApiReponse.<KoiFishPageResponse>builder().data(koiFishReponses).build();
    }


}
