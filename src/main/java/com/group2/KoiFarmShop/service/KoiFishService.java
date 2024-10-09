package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.CertificateRequest;
import com.group2.KoiFarmShop.dto.response.KoiFishDetailReponse;
import com.group2.KoiFarmShop.dto.response.KoiFishPageResponse;
import com.group2.KoiFarmShop.dto.response.KoiFishReponse;
import com.group2.KoiFarmShop.dto.request.KoiRequest;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class KoiFishService implements KoiFishServiceImp{
    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private KoiFishRepository koiFishRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CertificateRepository certificateRepository;
    @Override
    public KoiFishPageResponse getAllKoiFish(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("koiID").descending());
        Page<KoiFish> koiFishPage = koiFishRepository.findAll(pageable);
        List<KoiFishDetailReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishPage.getContent()) {
            KoiFishDetailReponse koiFishReponse = new KoiFishDetailReponse();
            koiFishReponse.setId(koiFish.getKoiID());
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.isGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());
            koiFishReponse.setFood(koiFish.getFood());
            koiFishReponse.setHealth(koiFish.getHealth());
            koiFishReponse.setPH(koiFish.getPH());
            koiFishReponse.setTemperature(koiFish.getTemperature());
            koiFishReponse.setWater(koiFish.getWater());
            koiFishReponse.setPurebred(koiFish.getPurebred());
            koiFishReponse.setStatus(koiFish.getStatus());

            koiFishReponseList.add(koiFishReponse);
        }
        return KoiFishPageResponse.builder()
                .pageNum(koiFishPage.getNumber()+1)
                .totalPages(koiFishPage.getTotalPages())
                .totalElements(koiFishPage.getTotalElements())
                .pageSize(koiFishPage.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }



    @Override
    public KoiFishPageResponse getKoiByCategory(Category category, int page,int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<KoiFish> koiFishList = koiFishRepository.findByCategory(category, pageable);
        List<KoiFishDetailReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishList) {
            KoiFishDetailReponse koiFishReponse = new KoiFishDetailReponse();
            koiFishReponse.setId(koiFish.getKoiID());
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.isGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());
            koiFishReponse.setFood(koiFish.getFood());
            koiFishReponse.setHealth(koiFish.getHealth());
            koiFishReponse.setPH(koiFish.getPH());
            koiFishReponse.setTemperature(koiFish.getTemperature());
            koiFishReponse.setWater(koiFish.getWater());
            koiFishReponse.setPurebred(koiFish.getPurebred());
            koiFishReponse.setStatus(koiFish.getStatus());

            koiFishReponseList.add(koiFishReponse);
        }
        return KoiFishPageResponse.builder()
                .pageNum(koiFishList.getNumber()+1)
                .totalPages(koiFishList.getTotalPages())
                .totalElements(koiFishList.getTotalElements())
                .pageSize(koiFishList.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }

    @Override
    public KoiFishDetailReponse getKoiFishById(int id) {
        if(id <= 0){
            throw new AppException(ErrorCode.INVALIDNUMBER);
        }
        KoiFish koiFish = koiFishRepository.findByKoiID(id);
        if(koiFish==null) {
            throw new AppException(ErrorCode.KOINOTFOUND);
        }
        List<KoiFishDetailReponse> koiList = getKoiByCategory(koiFish.getCategory(),1,3).getKoiFishReponseList();

        return KoiFishDetailReponse.builder()
                .id(koiFish.getKoiID())
                .age(koiFish.getAge())
                .gender(koiFish.isGender())
                .price(koiFish.getPrice())
                .koiImage(koiFish.getKoiImage())
                .size(koiFish.getSize())
                .personality(koiFish.getPersonality())
                .origin(koiFish.getOrigin())
                .categoryId(koiFish.getCategory().getCategoryID())
                .category(koiFish.getCategory().getCategoryName())
                .status(koiFish.getStatus())
                .purebred(koiFish.getPurebred())
                .health(koiFish.getHealth())
                .temperature(koiFish.getTemperature())
                .water(koiFish.getWater())
                .pH(koiFish.getPH())
                .food(koiFish.getFood())
                .build();
    }

    @Override
    public KoiFishDetailReponse addKoiFish(String origin,
                                     boolean gender,
                                     int age,
                                     double size,
                                     String personality,
                                     double price,
                                     int purebred,
                                     String health,
                                     String temperature,
                                     String water,
                                     String pH,
                                     String food,
                                     int categoryId,
                                     MultipartFile file,
                                     String name,
                                     MultipartFile certImg
                                     ) throws IOException {
        KoiFish koiFish = new KoiFish();
        koiFish.setOrigin(origin);
        koiFish.setGender(gender);
        koiFish.setAge(age);
        koiFish.setSize(size);
        koiFish.setPersonality(personality);
        koiFish.setPrice(price);
        koiFish.setPurebred(purebred);
        koiFish.setHealth(health);
        koiFish.setTemperature(temperature);
        koiFish.setWater(water);
        koiFish.setPH(pH);
        koiFish.setFood(food);
        koiFish.setKoiImage(firebaseService.uploadImage(file));
        Category category = categoryRepository.findByCategoryID(categoryId);
        koiFish.setCategory(category);
        KoiFish savedKoiFish= koiFishRepository.save(koiFish);
        Certificate newCertificate= certificateRepository.save(
                Certificate.builder()
                        .koiFish(savedKoiFish)
                        .name(name)
                        .createdDate(new Date())
                        .image(firebaseService.uploadImage(certImg))
                        .build());
        CertificateRequest certificateRequest = new CertificateRequest();
        certificateRequest.setName(newCertificate.getName());
        certificateRequest.setImage(newCertificate.getImage());
        certificateRequest.setCreatedDate(new Date());
        return KoiFishDetailReponse.builder()
                .id(savedKoiFish.getKoiID())
                .age(savedKoiFish.getAge())
                .gender(savedKoiFish.isGender())
                .price(savedKoiFish.getPrice())
                .koiImage(savedKoiFish.getKoiImage())
                .size(savedKoiFish.getSize())
                .personality(savedKoiFish.getPersonality())
                .origin(savedKoiFish.getOrigin())
                .categoryId(newCertificate.getId())
                .category(newCertificate.getName())
                .certificate(certificateRequest)
                .purebred(savedKoiFish.getPurebred())
                .health(savedKoiFish.getHealth())
                .temperature(savedKoiFish.getTemperature())
                .water(savedKoiFish.getWater())
                .pH(savedKoiFish.getPH())
                .food(savedKoiFish.getFood())
                .build();
    }

    @Override
    public KoiFishDetailReponse updateKoiFish(KoiRequest koiRequest, int id) {
        KoiFish koiFish = new KoiFish();
        koiFish.setKoiID(id);
        koiFish.setOrigin(koiRequest.getOrigin());
        koiFish.setGender(koiRequest.isGender());
        koiFish.setAge(koiRequest.getAge());
        koiFish.setSize(koiRequest.getSize());
        koiFish.setPersonality(koiRequest.getPersonality());
        koiFish.setPrice(koiRequest.getPrice());
        koiFish.setPurebred(koiRequest.getPurebred());
        koiFish.setHealth(koiRequest.getHealth());
        koiFish.setTemperature(koiRequest.getTemperature());
        koiFish.setWater(koiRequest.getWater());
        koiFish.setPH(koiRequest.getPH());
        koiFish.setFood(koiRequest.getFood());
        koiFish.setKoiImage(koiRequest.getKoiImage());
        Category category = categoryRepository.findByCategoryID(koiRequest.getCategoryId());
        koiFish.setCategory(category);
        KoiFish updateddKoiFish= koiFishRepository.save(koiFish);
        return KoiFishDetailReponse.builder()
                .id(updateddKoiFish.getKoiID())
                .age(updateddKoiFish.getAge())
                .gender(updateddKoiFish.isGender())
                .price(updateddKoiFish.getPrice())
                .koiImage(updateddKoiFish.getKoiImage())
                .size(updateddKoiFish.getSize())
                .personality(updateddKoiFish.getPersonality())
                .origin(updateddKoiFish.getOrigin())
                .categoryId(updateddKoiFish.getCategory().getCategoryID())
                .category(updateddKoiFish.getCategory().getCategoryName())
                .purebred(updateddKoiFish.getPurebred())
                .health(updateddKoiFish.getHealth())
                .temperature(updateddKoiFish.getTemperature())
                .water(updateddKoiFish.getWater())
                .pH(updateddKoiFish.getPH())
                .food(updateddKoiFish.getFood())
                .build();
    }
    public KoiFishPageResponse filterKoiFish(String categoryID,String maxSize,String minSize, String gender, String age, String minPrice, String maxPrice, String origin, int page, int pageSize,String sortField, String sortDirection,String sortField2,String sortDirection2,String purebred) {
        // Xử lý sortField1 và sortDirection1
        if (sortField == null || sortField.isEmpty()) {
            sortField = "koiID";
        }
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "asc";
        }
        if (sortDirection.equals("1")) {
            sortDirection = "asc";
        } else if (sortDirection.equals("2")) {
            sortDirection = "desc";
        } else {
            sortDirection = "asc";
        }

        // Tạo danh sách các điều kiện sắp xếp
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.fromString(sortDirection), sortField)); // Điều kiện sort chính

        // Xử lý sortField2 và sortDirection2 (nếu có)
        if (sortField2 != null && !sortField2.isEmpty()) {
            if (sortDirection2 == null || sortDirection2.isEmpty()) {
                sortDirection2 = "asc";
            }
            if (sortDirection2.equals("1")) {
                sortDirection2 = "asc";
            } else if (sortDirection2.equals("2")) {
                sortDirection2 = "desc";
            } else {
                sortDirection2 = "asc";
            }
            // Thêm điều kiện sort thứ hai
            orders.add(new Sort.Order(Sort.Direction.fromString(sortDirection2), sortField2));
        }

        // Áp dụng danh sách sắp xếp vào Sort
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        Specification<KoiFish> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(categoryID != null && !categoryID.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category").get("categoryID"), Integer.parseInt(categoryID)));
            }

            if (gender != null && !gender.isEmpty()) {
                Boolean genderValue = null;

                if (gender.equals("0")) {
                    genderValue = false;
                } else if (gender.equals("1")) {
                    genderValue = true;
                }

                if (genderValue != null) {
                    predicates.add(criteriaBuilder.equal(root.get("gender"), genderValue));
                }
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
            if (minPrice != null&& !minPrice.isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(minPrice)));
            }
            if (maxPrice != null&& !maxPrice.isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(maxPrice)));
            }
            if(origin != null && !origin.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("origin"), origin));
            }
            if(purebred != null && !purebred.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("purebred"),  Integer.parseInt(purebred)));
            }

            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("status"), 3));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        };
        Page<KoiFish> koiFishPage = koiFishRepository.findAll(spec,pageable);  // Gọi repository với phân trang

        List<KoiFishDetailReponse> koiFishReponseList = new ArrayList<>();
        for (KoiFish koiFish : koiFishPage.getContent()) {
            KoiFishDetailReponse koiFishReponse = new KoiFishDetailReponse();
            koiFishReponse.setId(koiFish.getKoiID());
            koiFishReponse.setOrigin(koiFish.getOrigin());
            koiFishReponse.setAge(koiFish.getAge());
            koiFishReponse.setSize(koiFish.getSize());
            koiFishReponse.setGender(koiFish.isGender());
            koiFishReponse.setPersonality(koiFish.getPersonality());
            koiFishReponse.setPrice(koiFish.getPrice());
            koiFishReponse.setKoiImage(koiFish.getKoiImage());
            koiFishReponse.setCategoryId(koiFish.getCategory().getCategoryID());
            koiFishReponse.setCategory(koiFish.getCategory().getCategoryName());
            koiFishReponse.setFood(koiFish.getFood());
            koiFishReponse.setHealth(koiFish.getHealth());
            koiFishReponse.setPH(koiFish.getPH());
            koiFishReponse.setTemperature(koiFish.getTemperature());
            koiFishReponse.setWater(koiFish.getWater());
            koiFishReponse.setPurebred(koiFish.getPurebred());
            koiFishReponse.setStatus(koiFish.getStatus());
            koiFishReponse.setPurebred(koiFish.getPurebred());
            koiFishReponseList.add(koiFishReponse);
        }
        return KoiFishPageResponse.builder()
                .pageNum(koiFishPage.getNumber()+1)
                .totalPages(koiFishPage.getTotalPages())
                .totalElements(koiFishPage.getTotalElements())
                .pageSize(koiFishPage.getSize())
                .koiFishReponseList(koiFishReponseList)
                .build();
    }

    @Override
    public List<KoiFishDetailReponse> compareKoiFish(int koiFishId1, int koiFishId2) {
        KoiFishDetailReponse koiFish1 = getKoiFishById(koiFishId1);
        KoiFishDetailReponse koiFish2 = getKoiFishById(koiFishId2);
        List<KoiFishDetailReponse> koiFishReponseList = new ArrayList<>();
        koiFishReponseList.add(koiFish1);
        koiFishReponseList.add(koiFish2);
        return koiFishReponseList;
    }

}
