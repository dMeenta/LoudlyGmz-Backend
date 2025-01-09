package com.example.loudlygmz.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loudlygmz.entity.Category;

public interface ICategoryDAO extends JpaRepository<Category, Integer> {
}
