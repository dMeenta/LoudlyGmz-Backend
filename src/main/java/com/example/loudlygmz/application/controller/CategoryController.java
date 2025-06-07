package com.example.loudlygmz.application.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.loudlygmz.application.dto.category.CategoryResponse;
import com.example.loudlygmz.domain.service.ICategoryService;
import com.example.loudlygmz.infrastructure.common.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "Categories Controller",
    description = "Controller for Categories of Game Communities"
)
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService categoryService;

    @Operation(
        summary = "List All Categories endpoint",
        description = "Endpoint to list all Categories registered on database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @GetMapping()
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Categorías obtenidas.",
                categoryService.getCategories())
        );
    }

    @Operation(
        summary = "Get a Category Data By its Name endpoint",
        description = "Endpoint to get an specific category data by its name on database"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
        responseCode = "200",
        description = "Http Status OK"
    )
    @GetMapping("/{categoryName}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryByName(@Schema(example = "acción") @PathVariable String categoryName) {
        return ResponseEntity.ok(
            ApiResponse.success(
                HttpStatus.OK.value(),
                "Categoría encontrada.",
                categoryService.getCategoryByName(categoryName))
        );
    }
}
