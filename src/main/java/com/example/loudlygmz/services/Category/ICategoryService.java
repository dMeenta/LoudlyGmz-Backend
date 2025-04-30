package com.example.loudlygmz.services.Category;

import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    ResponseEntity<?> getCategories();
    ResponseEntity<?> getCategoryById(int id);
}
