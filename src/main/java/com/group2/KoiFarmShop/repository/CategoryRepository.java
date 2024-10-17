package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.dto.response.CategoryReponse;
import com.group2.KoiFarmShop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Category findByCategoryID(Integer categoryID);
    public List<Category> findByCategoryNameContaining(String categoryName);
}
