package com.example.loudlygmz.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.loudlygmz.domain.model.Community;

public interface ICommunityRepository extends MongoRepository<Community, Integer> {
}