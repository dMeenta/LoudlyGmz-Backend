package com.example.loudlygmz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loudlygmz.domain.model.Category;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {
}
