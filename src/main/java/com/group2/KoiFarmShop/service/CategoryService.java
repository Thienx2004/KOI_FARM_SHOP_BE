package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.CategoryHomeReponse;
import com.group2.KoiFarmShop.dto.reponse.CategoryReponse;
import com.group2.KoiFarmShop.dto.reponse.KoiFishReponse;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.KoiFish;
import com.group2.KoiFarmShop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
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
    public List<CategoryReponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryReponse> categoryReponses = new ArrayList<>();
        for (Category c : categories) {

            CategoryReponse categoryReponse = new CategoryReponse();
            categoryReponse.setId(c.getCategoryID());
            categoryReponse.setCategoryName(c.getCategoryName());


            categoryReponses.add(categoryReponse);
        }
        return categoryReponses;
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

    @Transactional
    @Override
    public CategoryHomeReponse getListHomeCategory(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<Category> categoryHome = categories.getContent();
        List<CategoryReponse> categoryReponses = new ArrayList<>();
        CategoryHomeReponse categoryHomeReponse = new CategoryHomeReponse();

        for(Category category : categoryHome){
            CategoryReponse categoryReponse = new CategoryReponse();
            categoryReponse.setId(category.getCategoryID());
            categoryReponse.setCategoryName(category.getCategoryName());
            categoryReponse.setDescription(category.getDescription());
            categoryReponse.setCateImg(category.getCategoryImage());

            List<KoiFishReponse> koiFishList = new ArrayList<>();
            //for(KoiFish koiFish : category.getKoiFish()){
            for(int i = category.getKoiFish().size() - 3; i < category.getKoiFish().size(); i++){
                KoiFishReponse koiFishReponse = new KoiFishReponse();
                koiFishReponse.setId(category.getKoiFish().get(i).getKoiID());
                koiFishReponse.setOrigin(category.getKoiFish().get(i).getOrigin());
                koiFishReponse.setAge(category.getKoiFish().get(i).getAge());
                koiFishReponse.setGender(category.getKoiFish().get(i).isGender());
                koiFishReponse.setSize(category.getKoiFish().get(i).getSize());
                koiFishReponse.setPersonality(category.getKoiFish().get(i).getPersonality());
                koiFishReponse.setPrice(category.getKoiFish().get(i).getPrice());
                koiFishReponse.setCategoryId(category.getCategoryID());
                koiFishReponse.setCategory(category.getCategoryName());
                koiFishReponse.setKoiImage(category.getKoiFish().get(i).getKoiImage());
                koiFishReponse.setStatus(category.getKoiFish().get(i).getStatus());

                koiFishList.add(koiFishReponse);
            }
            categoryReponse.setKoiFishList(koiFishList);
            categoryReponses.add(categoryReponse);
        }
        categoryHomeReponse.setCategoryReponses(categoryReponses);
        categoryHomeReponse.setPageNum(pageNum);
        categoryHomeReponse.setPageSize(categories.getSize());
        categoryHomeReponse.setTotalElements(categories.getNumberOfElements());
        categoryHomeReponse.setTotalPages(categories.getTotalPages());

        return categoryHomeReponse;
    }
}
