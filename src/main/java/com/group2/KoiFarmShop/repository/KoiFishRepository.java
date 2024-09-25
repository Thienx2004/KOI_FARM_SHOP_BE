package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KoiFishRepository extends JpaRepository<KoiFish, Integer> {
    public List<KoiFish> getAllKoiFish();
    public List<KoiFish> getAllByCategory(Category category);
    Page<KoiFish> findByCategory(Category category, Pageable pageable);
    public KoiFish findByKoiID(int koiId);
    public KoiFish findBy(String koiFishName);
}
