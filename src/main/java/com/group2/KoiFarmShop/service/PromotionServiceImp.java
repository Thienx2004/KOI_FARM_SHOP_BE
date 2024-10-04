package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.response.PromotionDTO;

import java.util.Date;

public interface PromotionServiceImp {

    String createPromotion(String description, Date startDate, Date endDate, double discountRate);
    PromotionDTO assignPromotionToAccount(String promoCode, int accountId);
}
