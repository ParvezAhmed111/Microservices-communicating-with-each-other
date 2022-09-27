package com.project.service1.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.project.service1.models.Class;

@Repository
public interface ClassRepository extends ReactiveMongoRepository<Class, String> {

}
