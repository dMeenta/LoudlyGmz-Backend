package com.example.loudlygmz.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.loudlygmz.domain.model.MongoUser;

public interface IMongoUserRepository extends MongoRepository<MongoUser, String>{
  Optional<MongoUser> findByUsername(String username);
}
