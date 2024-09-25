package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.KoiFishPageResponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;

import java.util.List;

public interface KoiFishServiceImp {
    public KoiFishPageResponse getAllKoiFish(int page, int pageSize);
    public KoiFishPageResponse getKoiByCategory(Category category, int page,int pageSize);
    public KoiFishReponse getKoiFishById(int id);
    public KoiFishReponse addKoiFish(KoiRequest koiFish);
    public KoiFishReponse updateKoiFish(KoiRequest koiFish,int id);
    public KoiFishPageResponse filterKoiFish(String gender, Integer age, Double minPrice, Double maxPrice, String origin, Integer status,int page,int pageSize);
}
