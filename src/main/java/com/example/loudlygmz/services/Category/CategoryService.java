package com.example.loudlygmz.services.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.DAO.ICategoryDAO;
import com.example.loudlygmz.entity.Category;
import com.example.loudlygmz.utils.ApiResponse;

@Service
public class CategoryService implements ICategoryService {
    
    @Autowired
    private ICategoryDAO categoryDAO;

    @Override
    public ResponseEntity<?> getCategories() {
        try {
            List<Category> response = categoryDAO.findAll();
            return ResponseEntity.ok(ApiResponse.success(
                HttpStatus.OK.value(),
                "Categorías obtenidas exitosamente",
                response
                ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error de Servidor",
                    e.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<?> getCategoryById(int id) {
        try {
            Optional<Category> response = categoryDAO.findById(id);
            if(!response.isPresent()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                    ApiResponse.error(HttpStatus.NOT_FOUND.value(),
                        "Categoría no encontrada",
                        null)
                );
            }
            return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Categoría Encontrada Exitosamente", response));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Error de servidor",
                    e.getMessage())
            );
        }
    }

}
