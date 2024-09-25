package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface KoiFishRepository extends JpaRepository<KoiFish, Integer> {
    public List<KoiFish> getAllByCategory(Category category);
    Page<KoiFish> findByCategory(Category category, Pageable pageable);
    public KoiFish findByKoiID(int koiId);
    @Query("SELECT k FROM KoiFish k WHERE (:gender IS NULL OR k.gender = :gender) AND " +
            "(:age IS NULL OR k.age = :age) AND " +
            "(:minPrice IS NULL OR k.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR k.price <= :maxPrice) AND " +
            "(:origin IS NULL OR k.origin = :origin) AND " +
            "(:status IS NULL OR k.status = :status)")
    Page<KoiFish> filterKoiFish(@Param("gender") String gender,
                                @Param("age") Integer age,
                                @Param("minPrice") Double minPrice,
                                @Param("maxPrice") Double maxPrice,
                                @Param("origin") String origin,
                                @Param("status") Integer status,
                                Pageable pageable);  // Thêm Pageable để hỗ trợ phân trang
}

