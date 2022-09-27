package com.project.service2.services;

import com.project.service2.models.User;
import com.project.service1.models.Class;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IUserService {
	Mono<User> addUser(User user);
	Flux<User> getAllUsers();
	Mono<User> getUserById(String id);
	Mono<User> updateUser(String id, User user);
	Mono<Void> deleteUserById(String id);
	
	Flux<Class> getCLassDataFromService1();
}
