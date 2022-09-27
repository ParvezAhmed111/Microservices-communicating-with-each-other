package com.project.service2.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.project.service2.models.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

}
