package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.dto.response.CategoryReponse;
import com.group2.KoiFarmShop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findByCategoryID(Integer categoryID);
    public Page<Category> findByCategoryNameContaining(String categoryName, Pageable pageable);
    public Page<Category> findAll(Specification<Category>spec, Pageable pageable);
}
