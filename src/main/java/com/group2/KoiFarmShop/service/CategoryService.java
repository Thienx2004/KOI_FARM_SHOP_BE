package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.CategoryHomeReponse;
import com.group2.KoiFarmShop.dto.reponse.CategoryReponse;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements CategoryServiceImp{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public String addCategory(Category category) {

        return "";
    }

    @Override
    public Category getCategoryById(int id) {
        return null;
    }

    @Override
    public String updateCategory(Category category) {
        return "";
    }

    @Override
    public String deleteCategory(int id) {
        return "";
    }

    @Override
    public List<Category> getListHomeCategory(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<Category> categoryHome = categories.getContent();
        List<CategoryReponse> categoryReponses = new ArrayList<>();
        CategoryHomeReponse categoryHomeReponse = new CategoryHomeReponse();

        for(Category category : categoryHome){
            CategoryReponse categoryReponse = new CategoryReponse();
            categoryReponse.setCategoryName(category.getCategoryName());
            categoryReponse.setDescription(category.getDescription());
            categoryReponse.setCateImg(category.getCategoryImage());

            for(KoiFish koiFish : category.getKoiFish()){

            }
        }

        return List.of();
    }
}
