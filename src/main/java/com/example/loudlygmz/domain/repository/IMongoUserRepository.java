package com.example.loudlygmz.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.loudlygmz.domain.model.MongoUser;

public interface IMongoUserRepository extends MongoRepository<MongoUser, String>{
}
