package com.example.loudlygmz.infrastructure.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.category.CategoryResponse;
import com.example.loudlygmz.domain.model.Category;
import com.example.loudlygmz.domain.repository.ICategoryRepository;
import com.example.loudlygmz.domain.service.ICategoryService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    
    private final ICategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID de la categoría debe ser mayor que cero.");
        }

        return categoryRepository.findById(id)
        .map(this::toResponse)
        .orElseThrow(()->new EntityNotFoundException("Categoría no encontrada"));
    }

    private CategoryResponse toResponse(Category category){
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
