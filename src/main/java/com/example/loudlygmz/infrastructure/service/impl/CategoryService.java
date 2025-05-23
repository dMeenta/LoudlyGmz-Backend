package com.example.loudlygmz.infrastructure.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.loudlygmz.application.dto.category.CategoryResponse;
import com.example.loudlygmz.application.exception.CategoryValidationException;
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
    public CategoryResponse getCategoryByName(String categoryName) {
        return categoryRepository.findByName(categoryName)
        .map(this::toResponse)
        .orElseThrow(()-> new EntityNotFoundException(
            String.format("Categoría con el nombre '%s' no encontrada", categoryName)));
    }

    @Override
    public List<Category> getListOfCategoriesByName(List<String> arrayCategoriesNames) {
        List<Category> categories = categoryRepository.findByNameIn(arrayCategoriesNames);

        Map<String, String> resultMap = new LinkedHashMap<>();
        List<Category> resolved = new ArrayList<>();

        for(String name : arrayCategoriesNames){
            boolean matched = false;
            for(Category category : categories){
                if(category.getName().equals(name)){
                    matched = true;
                    resolved.add(category);
                    break;
                }
            }
            resultMap.put(name, matched ? "found" : "not found");
        }

        boolean allValid = resultMap.values().stream().allMatch(status -> status.equals("found"));
        if(!allValid){
            throw new CategoryValidationException(resultMap);
        }

        return resolved;
    }

    private CategoryResponse toResponse(Category category){
        CategoryResponse dto = new CategoryResponse();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
