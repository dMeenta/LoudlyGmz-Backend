package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.CategoryResponse;
import com.example.loudlygmz.domain.service.ICategoryService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Categorías obtenidas.",
                categoryService.getCategories())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Categoría encontrada.",
                categoryService.getCategoryById(id))
        );
    }
}
