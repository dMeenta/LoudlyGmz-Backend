package com.example.loudlygmz.domain.service;

import java.util.List;

import com.example.loudlygmz.application.dto.category.CategoryResponse;
import com.example.loudlygmz.domain.model.Category;

public interface ICategoryService {
    List<CategoryResponse> getCategories();
    CategoryResponse getCategoryByName(String categoryName);
    List<Category> getListOfCategoriesByName(List<String> arrayCategoriesNames);
}
