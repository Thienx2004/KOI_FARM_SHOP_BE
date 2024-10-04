package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.response.PromotionDTO;
import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.Promotion;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PromotionService implements PromotionServiceImp{

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public String createPromotion(String description, Date startDate, Date endDate, double discountRate) {

        Promotion promotion = new Promotion();

        promotion.setPromoCode(generatePromoCode());
        promotion.setDesciption(description);
        promotion.setStartDate(startDate);
        promotion.setEndDate(endDate);
        promotion.setDiscountRate(discountRate);
        promotion.setStatus(true);

        promotionRepository.save(promotion);
        return "Tạo mã giảm giá thành công!";
    }

    @Override
    public PromotionDTO assignPromotionToAccount(String promoCode, int accountId) {

        Account account = accountRepository.findByAccountID(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.INVALIDACCOUNT));
        Promotion promotion = promotionRepository.findByPromoCode(promoCode)
                .orElseThrow(() -> new AppException(ErrorCode.PROMOTION_INVALID));

        if(isPromoCodeValidForAccount(promotion, account)) {
            PromotionDTO promotionDTO = new PromotionDTO();
            promotionDTO.setPromoCode(promoCode);
            promotionDTO.setPromotionID(promotion.getPromotionID());
            promotionDTO.setStartDate(promotion.getStartDate());
            promotionDTO.setEndDate(promotion.getEndDate());
            promotionDTO.setDiscountRate(promotion.getDiscountRate());
            promotionDTO.setDesciption(promotion.getDesciption());
            promotionDTO.setStatus(promotion.isStatus());

            return promotionDTO;
        }
        return null;
    }

    private String generatePromoCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public boolean isPromoCodeValidForAccount(Promotion promotion, Account account) {

        if (promotion == null || !promotion.isStatus()) {
            return false;
        }

        Date now = new Date();
        if (now.before(promotion.getStartDate()) || now.after(promotion.getEndDate())) {
            return false;
        }

        if (account.getPromotion() != null && account.getPromotion().getPromoCode().equals(promotion.getPromoCode())) {
            return false;
        }

        return true;
    }


}
