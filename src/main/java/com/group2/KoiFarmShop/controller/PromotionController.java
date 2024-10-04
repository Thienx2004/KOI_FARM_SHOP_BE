package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.response.ApiReponse;
import com.group2.KoiFarmShop.dto.response.PromotionDTO;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.service.PromotionService;
import com.group2.KoiFarmShop.service.PromotionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    private PromotionServiceImp promotionService;

    @PostMapping("/create")
    public ApiReponse<String> createPromotion(
            @RequestParam String description,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
            @RequestParam double discountRate) {

        String success = promotionService.createPromotion(description, startDate, endDate, discountRate);
        ApiReponse<String> apiReponse = new ApiReponse<>();
        apiReponse.setData(success);
        return apiReponse;
    }

    @PostMapping("/apply")
    public ApiReponse<PromotionDTO> applyPromotionToAccount(
            @RequestParam String promoCode,
            @RequestParam int accountId){

        ApiReponse<PromotionDTO> apiReponse = new ApiReponse<>();
        PromotionDTO promotionDTO = promotionService.assignPromotionToAccount(promoCode, accountId);
        if(promotionDTO != null){
            apiReponse.setData(promotionDTO);
            return apiReponse;
        }
        throw new AppException(ErrorCode.PROMOTION_INVALID);
    }
}
