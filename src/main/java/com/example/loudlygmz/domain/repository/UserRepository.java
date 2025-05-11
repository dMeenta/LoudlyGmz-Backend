package com.example.loudlygmz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loudlygmz.domain.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
