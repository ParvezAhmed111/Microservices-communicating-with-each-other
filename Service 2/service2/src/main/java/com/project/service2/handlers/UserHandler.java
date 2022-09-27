package com.project.service2.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.service2.models.User;
import com.project.service2.services.IUserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
 
@Component
public class UserHandler {

	@Autowired
	private IUserService userService;
	
	private Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	
	//POST
		public Mono<ServerResponse> addUser(ServerRequest request) {
			Mono<User> userMono= request.bodyToMono(User.class);
			
			return userMono.flatMap(r-> ServerResponse.ok()
									.contentType(MediaType.APPLICATION_JSON)
									.body(userService.addUser(r), User.class));
		}
		
		//GET
		public Mono<ServerResponse> getAllUsers(ServerRequest request) {
			Flux<User> userFlux= userService.getAllUsers();
			
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(userFlux, User.class); 
		}

		public Mono<ServerResponse> getUserById(ServerRequest request) {
			String id= request.pathVariable("id");
			Mono<User> userMono= userService.getUserById(id);
			
			return userMono.flatMap(r-> ServerResponse.ok()
									.contentType(MediaType.APPLICATION_JSON)
									.body(userMono, User.class))
					.switchIfEmpty(notFound);	
		}
		
		//PUT
		public Mono<ServerResponse> updateUser(ServerRequest serverRequest){
			String id= serverRequest.pathVariable("id");
			Mono<User> userMono= serverRequest.bodyToMono(User.class);	

			return userMono.flatMap(r -> ServerResponse.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(userService.updateUser(id, r), User.class));
		}
		
		//DELETE
		public Mono<ServerResponse> deleteUserById(ServerRequest serverRequest){
			String id= serverRequest.pathVariable("id");
			Mono<Void> userMono= userService.deleteUserById(id);
			return ServerResponse.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(userMono, User.class);
		}
		
		
		public Mono<ServerResponse> getCLassDataFromService1(ServerRequest serverRequest) {
			Flux<com.project.service1.models.Class> responseMono= userService.getCLassDataFromService1();
			return ServerResponse.ok() 
					.contentType(MediaType.APPLICATION_JSON)
					.body(responseMono, com.project.service1.models.Class.class);
		}

}
