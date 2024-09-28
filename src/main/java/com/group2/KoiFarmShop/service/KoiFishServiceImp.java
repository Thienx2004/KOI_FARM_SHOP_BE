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
    public KoiFishPageResponse filterKoiFish(String categoryID,String minSize,String maxSize, String gender, String age, String minPrice, String maxPrice, String origin, int page, int pageSize,String sortField, String sortDirection);
    public List<KoiFishReponse> compareKoiFish(int koiFishId1, int koiFishId2);
}
