package com.project.service2.routers;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.service2.handlers.UserHandler;

@Configuration
public class Service2Routers {
	
	private static final String	USER_PATH="user";
	private static final String	ID="/{id}";
	private static final String	API="/api";
	
//	@Bean
//	public RouterFunction<ServerResponse> checkAPIs(UserHandler userHandler){
//		return route(GET("/"+USER_PATH+API), userHandler :: getCLassDataFromService1);
//	}
//	
	@Bean  
	public RouterFunction<ServerResponse> userAPIs(UserHandler userHandler){
		return route(POST("/"+ USER_PATH), userHandler :: addUser)
				.andRoute(GET("/"+USER_PATH+API), userHandler :: getCLassDataFromService1)
				.andRoute(GET("/"+ USER_PATH + "s"), userHandler :: getAllUsers)
				.andRoute(GET("/"+ USER_PATH + ID), userHandler :: getUserById)
				.andRoute(PUT("/"+ USER_PATH + ID), userHandler :: updateUser)
				.andRoute(DELETE("/"+ USER_PATH + ID), userHandler :: deleteUserById);
	}
}
