package com.example.loudlygmz.domain.service;

import java.util.List;

import com.example.loudlygmz.application.dto.category.CategoryResponse;

public interface ICategoryService {
    List<CategoryResponse> getCategories();
    CategoryResponse getCategoryById(Integer id);
}
