package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.service.KoiFishServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KoiFishController {
    @Autowired
    KoiFishServiceImp koiFishService;
    // Lấy toàn bộ danh sách cá Koi
    @GetMapping("/allkoi")
    public ApiReponse<List<KoiFishReponse>> getAllKoiFish() {
        List<KoiFishReponse> koiFishList = koiFishService.getAllKoiFish();
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishList).build();
    }

    // Lấy danh sách cá Koi theo Category
    @GetMapping("/category/{categoryId}")
    public ApiReponse<List<KoiFishReponse>> getAllKoiFishByCategory(@PathVariable("categoryId") Category category) {
        List<KoiFishReponse> koiFishList = koiFishService.getAllKoiFishByCategory(category);
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishList).build();
    }

    // Lấy 5 cá Koi theo Category và Trang
    @GetMapping("/category/{categoryId}/page/{page}")
    public ApiReponse<List<KoiFishReponse>> get5ByCategory(@PathVariable("categoryId") Category category, @PathVariable("page") int page) {
        List<KoiFishReponse> koiFishList = koiFishService.get5ByCategory(category, page);
        return ApiReponse.<List<KoiFishReponse>>builder().data(koiFishList).build();
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
    public ApiReponse<KoiFishReponse> addKoiFish(@RequestBody KoiFish koiFish) {
        KoiFishReponse koiFishReponse=koiFishService.addKoiFish(koiFish);
        return ApiReponse.<KoiFishReponse>builder().data(koiFishReponse).build();
    }


    // Cập nhật cá Koi
    @PutMapping("/update/{id}")
    public ApiReponse<KoiFishReponse> editKoiFish(@PathVariable int id, @RequestBody KoiFish koiFish) {
        koiFish.setKoiID(id);
        KoiFishReponse koiFishReponse = koiFishService.updateKoiFish(koiFish);
        return ApiReponse.<KoiFishReponse>builder().data(koiFishReponse).build();
    }

}
