package com.project.service1.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.service1.handlers.ClassHandler;
import com.project.service1.handlers.SchoolHandler;

@Configuration
public class Service1Routers {
	
	private static final String	SCHOOL_PATH="school";
	private static final String	CLASS_PATH="class";
	private static final String	ID="/{id}";
	
	
	@Bean 
	public RouterFunction<ServerResponse> studentAPIs(SchoolHandler schoolHandler){
		return route(POST("/"+ SCHOOL_PATH), schoolHandler :: addSchool)
			.andRoute(GET("/"+ SCHOOL_PATH + "s"), schoolHandler :: getAllSchools)
			.andRoute(GET("/"+ SCHOOL_PATH + ID), schoolHandler :: getSchoolById)
			.andRoute(PUT("/"+ SCHOOL_PATH + ID), schoolHandler :: updateSchool)
			.andRoute(DELETE("/"+ SCHOOL_PATH + ID), schoolHandler :: deleteSchoolById);
	}
	
	@Bean 
	public RouterFunction<ServerResponse> classAPIs(ClassHandler classHandler){
		return route(POST("/"+ CLASS_PATH), classHandler :: addClass)
			.andRoute(GET("/"+ CLASS_PATH + "es"), classHandler :: getAllClasses)
			.andRoute(GET("/"+ CLASS_PATH + ID), classHandler :: getClassById)
			.andRoute(PUT("/"+ CLASS_PATH + ID), classHandler :: updateClass)
			.andRoute(DELETE("/"+ CLASS_PATH + ID), classHandler :: deleteClassById);
	} 
}
