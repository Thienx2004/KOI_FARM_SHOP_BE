package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
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
    public ApiReponse<List<KoiFishReponse>> getAllKoiFish() {
        List<KoiFishReponse> koiFishList = koiFishService.getAllKoiFish();
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishList).statusCode(200).build();
    }

    // Lấy danh sách cá Koi theo Category
    @GetMapping("/category/{categoryId}")
    public ApiReponse<List<KoiFishReponse>> getAllKoiFishByCategory(@PathVariable("categoryId") int id) {
        Category category=categoryRepository.findByCategoryID(id);
        List<KoiFishReponse> koiFishList = koiFishService.getAllKoiFishByCategory(category);
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishList).statusCode(200).build();
    }

    // Lấy 5 cá Koi theo Category và Trang
    @GetMapping("/category")
    public ApiReponse<List<KoiFishReponse>> get5ByCategory(@RequestParam("categoryId") int id, @RequestParam("page") int page) {
        Category category=categoryRepository.findByCategoryID(id);
        List<KoiFishReponse> koiFishList = koiFishService.get5ByCategory(category, page);
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishList).statusCode(200).build();
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
    public ApiReponse<List<KoiFishReponse>> filterKoiFish(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) Integer status) {

        List<KoiFishReponse> koiFishReponses = koiFishService.filterKoiFish(gender, age, minPrice, maxPrice, origin, status);
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishReponses).build();
    }


}
