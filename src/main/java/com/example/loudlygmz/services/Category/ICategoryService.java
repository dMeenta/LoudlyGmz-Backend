package com.example.loudlygmz.services.Category;

import java.util.List;
import java.util.Optional;

import com.example.loudlygmz.entity.Category;

public interface ICategoryService {
    public List<Category> getCategories();

    public Optional<Category> getCategoryById(int id);
}
