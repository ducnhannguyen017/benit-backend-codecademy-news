package com.benit.backend_codecademy_news.service;

import com.benit.backend_codecademy_news.entity.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    Category deleteCategory(Long categoryId) ;
    Category updateCategory(Long categoryId, Category category) ;
    Category getCategoryByTag(String categoryTag) ;
    Category getCategoryById(Long id);
    List<Category> getCategories();
}
