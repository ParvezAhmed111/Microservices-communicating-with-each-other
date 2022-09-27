package com.project.service2.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.service1.models.Class;
import com.project.service2.models.User;
import com.project.service2.repository.UserRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserRepository userRepository;
	

	private static final String BASE_URL = "http://localhost:8081/";
	
	WebClient webClient = WebClient.create();
	
	@Override 
	public Flux<Class> getCLassDataFromService1() {	
		return webClient.get()
				.uri(BASE_URL+"classes")
				.retrieve()
				.bodyToFlux(Class.class);	
	}

	@Override
	public Mono<User> addUser(User user) {
		String id=user.getClassId();
		return webClient.get()		// adding data when the class id is present
				.uri(BASE_URL+"class/"+id)
				.retrieve()
				.bodyToMono(Class.class)
				.flatMap(r->{
					return userRepository.save(user);
				});	//on error resume
	}

	@Override
	public Mono<User> getUserById(String id) {
		return userRepository.findById(id);
	}
	
	@Override
	public Flux<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public Mono<User> updateUser(String id, User user) {
		Mono<User> existingUser= userRepository.findById(id);
		if(existingUser.hasElement()!=null) {
			return existingUser.flatMap(r->{
									r.setUserId(id);
									r.setUserName(user.getUserName());
									r.setUserRole(user.getUserRole());
									r.setClassId(user.getClassId());
									return userRepository.save(r);
								});
		}
		return null;
	}

	@Override
	public Mono<Void> deleteUserById(String id) {
		Mono<User> toDelete= userRepository.findById(id);
		if(toDelete.hasElement()!=null)
			return userRepository.deleteById(id);
		return null;
	}

}
