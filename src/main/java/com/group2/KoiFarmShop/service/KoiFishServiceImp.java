package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;

import java.util.List;

public interface KoiFishServiceImp {
    public List<KoiFishReponse> getAllKoiFish();
    public List<KoiFishReponse> getAllKoiFishByCategory(Category category);
    public List<KoiFishReponse> get5ByCategory(Category category, int page);
    public KoiFishReponse getKoiFishById(int id);
    public KoiFishReponse addKoiFish(KoiRequest koiFish);
    public KoiFishReponse updateKoiFish(KoiRequest koiFish,int id);
}
