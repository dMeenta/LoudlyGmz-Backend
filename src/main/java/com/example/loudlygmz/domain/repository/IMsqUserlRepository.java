package com.example.loudlygmz.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.loudlygmz.domain.model.MsqlUser;


public interface IMsqUserlRepository extends JpaRepository<MsqlUser, String> {
  Optional<MsqlUser> findByUsername(String username);
}
