package com.example.loudlygmz.services.Category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loudlygmz.DAO.ICategoryDAO;
import com.example.loudlygmz.entity.Category;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryDAO categoryDAO;

    @Override
    public List<Category> getCategories() {
        return categoryDAO.findAll();
    }

    @Override
    public Optional<Category> getCategoryById(int id) {
        return categoryDAO.findById(id);
    }

}
