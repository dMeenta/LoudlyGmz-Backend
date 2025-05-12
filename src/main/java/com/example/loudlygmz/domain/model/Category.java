package com.example.loudlygmz.domain.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name, description;
    
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @ManyToMany(mappedBy = "categories") // Relación inversa, gestionada por Category
    @JsonBackReference
    private List<Game> games;

}
