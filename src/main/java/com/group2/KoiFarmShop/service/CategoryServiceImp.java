package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.response.CategoryHomeReponse;
import com.group2.KoiFarmShop.dto.response.CategoryReponse;
import com.group2.KoiFarmShop.entity.Category;

import java.util.List;

public interface CategoryServiceImp {

    public List<CategoryReponse> getAllCategories();
    public String addCategory(Category category);
    public Category getCategoryById(int id);
    public String updateCategory(Category category);
    public String deleteCategory(int id);

    public CategoryHomeReponse getListHomeCategory(int pageNum, int pageSize);
}
