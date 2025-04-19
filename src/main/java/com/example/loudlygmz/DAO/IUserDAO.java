package com.example.loudlygmz.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loudlygmz.entity.User;

public interface IUserDAO extends JpaRepository<User, String> {
    
}
