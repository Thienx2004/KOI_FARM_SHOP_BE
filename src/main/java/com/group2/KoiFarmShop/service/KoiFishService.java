package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.KoiFishPageResponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.Certificate;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.repository.CategoryRepository;
import com.group2.KoiFarmShop.repository.CertificateRepository;
import com.group2.KoiFarmShop.repository.KoiFishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class KoiFishService implements KoiFishServiceImp{

    @Autowired
    private KoiFishRepository koiFishRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    @Override
    public KoiFishPageResponse getAllKoiFish(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<KoiFish> koiFishPage = koiFishRepository.findAll(pageable);
        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishPage) {
            KoiFishReponse koiFishReponse = new KoiFishReponse();
            koiFishReponse.setId(koiFish.getKoiID());
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.getGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());
            koiFishReponseList.add(koiFishReponse);
        }
        return KoiFishPageResponse.builder()
                .pageNum(koiFishPage.getNumber())
                .totalPages(koiFishPage.getTotalPages())
                .totalElements(koiFishPage.getNumberOfElements())
                .pageSize(koiFishPage.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }



    @Override
    public KoiFishPageResponse getKoiByCategory(Category category, int page,int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        Page<KoiFish> koiFishList = koiFishRepository.findByCategory(category, pageable);
        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishList) {
            KoiFishReponse koiFishReponse = new KoiFishReponse();
            koiFishReponse.setId(koiFish.getKoiID());
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.getGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());

            koiFishReponseList.add(koiFishReponse);
        }
        return KoiFishPageResponse.builder()
                .pageNum(koiFishList.getNumber())
                .totalPages(koiFishList.getTotalPages())
                .totalElements(koiFishList.getNumberOfElements())
                .pageSize(koiFishList.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }

    @Override
    public KoiFishReponse getKoiFishById(int id) {
        KoiFish koiFish=koiFishRepository.findByKoiID(id);
        return KoiFishReponse.builder()
                .id(koiFish.getKoiID())
                .age(koiFish.getAge())
                .gender(koiFish.getGender())
                .price(koiFish.getPrice())
                .koiImage(koiFish.getKoiImage())
                .size(koiFish.getSize())
                .personality(koiFish.getPersonality())
                .origin(koiFish.getOrigin())
                .categoryId(koiFish.getCategory().getCategoryID())
                .category(koiFish.getCategory().getCategoryName())
                .build();
    }

    @Override
    public KoiFishReponse addKoiFish(KoiRequest koiRequest) {
        KoiFish koiFish = new KoiFish();
        koiFish.setOrigin(koiRequest.getOrigin());
        koiFish.setGender(koiRequest.getGender());
        koiFish.setAge(koiRequest.getAge());
        koiFish.setSize(koiRequest.getSize());
        koiFish.setPersonality(koiRequest.getPersonality());
        koiFish.setPrice(koiRequest.getPrice());
        koiFish.setKoiImage(koiRequest.getKoiImage());
        Category category = categoryRepository.findByCategoryID(koiRequest.getCategoryId());
        koiFish.setCategory(category);
        KoiFish savedKoiFish= koiFishRepository.save(koiFish);
        Certificate newCertificate= certificateRepository.save(
                Certificate.builder()
                        .koiFish(savedKoiFish)
                        .name(koiRequest.getCertificate().getName())
                        .createdDate(koiRequest.getCertificate().getCreatedDate())
                        .image(koiRequest.getCertificate().getImage())
                        .build());
        return KoiFishReponse.builder()
                .id(savedKoiFish.getKoiID())
                .age(savedKoiFish.getAge())
                .gender(savedKoiFish.getGender())
                .price(savedKoiFish.getPrice())
                .koiImage(savedKoiFish.getKoiImage())
                .size(savedKoiFish.getSize())
                .personality(savedKoiFish.getPersonality())
                .origin(savedKoiFish.getOrigin())
                .categoryId(newCertificate.getId())
                .category(newCertificate.getName())
                .build();
    }

    @Override
    public KoiFishReponse updateKoiFish(KoiRequest koiRequest,int id) {
        KoiFish koiFish = new KoiFish();
        koiFish.setKoiID(id);
        koiFish.setOrigin(koiRequest.getOrigin());
        koiFish.setGender(koiRequest.getGender());
        koiFish.setAge(koiRequest.getAge());
        koiFish.setSize(koiRequest.getSize());
        koiFish.setPersonality(koiRequest.getPersonality());
        koiFish.setPrice(koiRequest.getPrice());
        koiFish.setKoiImage(koiRequest.getKoiImage());
        Category category = categoryRepository.findByCategoryID(koiRequest.getCategoryId());
        koiFish.setCategory(category);
        KoiFish updateddKoiFish= koiFishRepository.save(koiFish);
        return KoiFishReponse.builder()
                .id(updateddKoiFish.getKoiID())
                .age(updateddKoiFish.getAge())
                .gender(updateddKoiFish.getGender())
                .price(updateddKoiFish.getPrice())
                .koiImage(updateddKoiFish.getKoiImage())
                .size(updateddKoiFish.getSize())
                .personality(updateddKoiFish.getPersonality())
                .origin(updateddKoiFish.getOrigin())
                .categoryId(updateddKoiFish.getCategory().getCategoryID())
                .category(updateddKoiFish.getCategory().getCategoryName())
                .build();
    }
    public KoiFishPageResponse filterKoiFish(String gender, Integer age, Double minPrice, Double maxPrice, String origin, Integer status, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);  // Tạo đối tượng Pageable cho phân trang
        Page<KoiFish> koiFishPage = koiFishRepository.filterKoiFish(gender, age, minPrice, maxPrice, origin, status, pageable);  // Gọi repository với phân trang
        List<KoiFish> koiFishList = koiFishPage.getContent();  // Lấy danh sách koi từ trang hiện tại

        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishList) {
            KoiFishReponse koiFishReponse = new KoiFishReponse();
            koiFishReponse.setId(koiFish.getKoiID());
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.getGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());

            koiFishReponseList.add(koiFishReponse);
        }
        return KoiFishPageResponse.builder()
                .pageNum(koiFishPage.getNumber())
                .totalPages(koiFishPage.getTotalPages())
                .totalElements(koiFishPage.getNumberOfElements())
                .pageSize(koiFishPage.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }

}
