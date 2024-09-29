package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.KoiFishPageResponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
import com.group2.KoiFarmShop.entity.Batch;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.Certificate;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.CategoryRepository;
import com.group2.KoiFarmShop.repository.CertificateRepository;
import com.group2.KoiFarmShop.repository.KoiFishRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
                .pageNum(koiFishPage.getNumber()+1)
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
                .pageNum(koiFishList.getNumber()+1)
                .totalPages(koiFishList.getTotalPages())
                .totalElements(koiFishList.getNumberOfElements())
                .pageSize(koiFishList.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }

    @Override
    public KoiFishReponse getKoiFishById(int id) {
        KoiFish koiFish=koiFishRepository.findByKoiID(id);
        if(koiFish==null) {
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
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
    public KoiFishPageResponse filterKoiFish(String categoryID,String maxSize,String minSize, String gender, String age, String minPrice, String maxPrice, String origin, int page, int pageSize,String sortField, String sortDirection) {
        if (sortField == null || sortField.isEmpty()) {
            sortField = "koiID";
        }
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "asc";
        }
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        Pageable pageable = PageRequest.of(page - 1, pageSize,sort);  // Tạo đối tượng Pageable cho phân trang
        Specification<KoiFish> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(categoryID != null && !categoryID.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("categoryID"), Integer.parseInt(categoryID)));
            }

            if(gender != null&& !gender.isEmpty()) {
                Boolean genderValue = Boolean.valueOf(gender);
                predicates.add(criteriaBuilder.equal(root.get("gender"), genderValue));
            }
            if (minSize != null && !minSize.isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("size"), Double.parseDouble(minSize)));
            }
            if (maxSize != null && !maxSize.isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("size"), Double.parseDouble(maxSize)));
            }
            if (age != null && !age.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("age"), Integer.parseInt(age)));
            }
            // Lọc theo minPrice và maxPrice
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(minPrice)));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(maxPrice)));
            }
            if(origin != null && !origin.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("origin"), origin));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
        Page<KoiFish> koiFishPage = koiFishRepository.findAll(spec,pageable);  // Gọi repository với phân trang

        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishPage.getContent()) {
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
                .pageNum(koiFishPage.getNumber()+1)
                .totalPages(koiFishPage.getTotalPages())
                .totalElements(koiFishPage.getNumberOfElements())
                .pageSize(koiFishPage.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }

    @Override
    public List<KoiFishReponse> compareKoiFish(int koiFishId1, int koiFishId2) {
        KoiFishReponse koiFish1 = getKoiFishById(koiFishId1);
        KoiFishReponse koiFish2 = getKoiFishById(koiFishId2);
        List<KoiFishReponse> koiFishReponseList = new ArrayList<>();
        koiFishReponseList.add(koiFish1);
        koiFishReponseList.add(koiFish2);
        return koiFishReponseList;
    }

}
