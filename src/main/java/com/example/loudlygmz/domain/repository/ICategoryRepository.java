package com.example.loudlygmz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loudlygmz.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {
  Optional<Category> findByName(String name);
  List<Category> findByNameIn(List<String> listOfCategoriesNames);
}
