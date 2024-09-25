package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.repository.KoiFishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class KoiFishService implements KoiFishServiceImp{

    @Autowired
    private KoiFishRepository koiFishRepository;

    @Override
    public List<KoiFishReponse> getAllKoiFish() {
        List<KoiFish> koiFishList = koiFishRepository.findAll();
        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishList) {
            KoiFishReponse koiFishReponse = new KoiFishReponse();
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.getGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponseList.add(koiFishReponse);
        }
        return koiFishReponseList;
    }

    @Override
    public List<KoiFishReponse> getAllKoiFishByCategory(Category category) {
        List<KoiFish> koiFishList = koiFishRepository.getAllByCategory(category);
        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishList) {
            KoiFishReponse koiFishReponse = new KoiFishReponse();
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.getGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponseList.add(koiFishReponse);
        }
        return koiFishReponseList;
    }
    @Override
    public List<KoiFishReponse> get5ByCategory(Category category, int page) {
        Pageable pageable = PageRequest.of(page-1, 5);
        List<KoiFish> koiFishList = koiFishRepository.findByCategory(category, pageable).getContent();
        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishList) {
            KoiFishReponse koiFishReponse = new KoiFishReponse();
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.getGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponseList.add(koiFishReponse);
        }
        return koiFishReponseList;// mỗi trang chứa 5 kết quả
    }

    @Override
    public KoiFishReponse getKoiFishById(int id) {
        KoiFish koiFish=koiFishRepository.findByKoiID(id);
        return KoiFishReponse.builder()
                .age(koiFish.getAge())
                .gender(koiFish.getGender())
                .price(koiFish.getPrice())
                .koiImage(koiFish.getKoiImage())
                .size(koiFish.getSize())
                .personality(koiFish.getPersonality())
                .origin(koiFish.getOrigin())
                .build();
    }

    @Override
    public KoiFishReponse addKoiFish(KoiFish koiFish) {
        KoiFish savedKoiFish= koiFishRepository.save(koiFish);
        return KoiFishReponse.builder()
                .age(savedKoiFish.getAge())
                .gender(savedKoiFish.getGender())
                .price(savedKoiFish.getPrice())
                .koiImage(savedKoiFish.getKoiImage())
                .size(savedKoiFish.getSize())
                .personality(savedKoiFish.getPersonality())
                .origin(savedKoiFish.getOrigin())
                .build();
    }

    @Override
    public KoiFishReponse updateKoiFish(KoiFish koiFish) {
        KoiFish updateddKoiFish= koiFishRepository.save(koiFish);
        return KoiFishReponse.builder()
                .age(updateddKoiFish.getAge())
                .gender(updateddKoiFish.getGender())
                .price(updateddKoiFish.getPrice())
                .koiImage(updateddKoiFish.getKoiImage())
                .size(updateddKoiFish.getSize())
                .personality(updateddKoiFish.getPersonality())
                .origin(updateddKoiFish.getOrigin())
                .build();
    }
}
