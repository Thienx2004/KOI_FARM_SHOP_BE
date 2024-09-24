package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.entity.Category;

import java.util.List;

public interface CategoryServiceImp {

    public List<Category> getAllCategories();
    public String addCategory(Category category);
    public Category getCategoryById(int id);
}