package com.project.servicesmerge.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.service1.models.School;
import com.project.servicesmerge.models.ClassDetails;
import com.project.servicesmerge.models.SchoolDetails;
import com.project.servicesmerge.models.UserDetails;
import com.project.servicesmerge.services.IProjectService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class Handler {
	
	@Autowired
	private IProjectService service;
	
//	/school
	public Mono<ServerResponse> getAllSchoolDetails(ServerRequest serverRequest) {
		Flux<SchoolDetails> responseMono= service.getAllSchoolDetails();
		return ServerResponse.ok() 
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMono, School.class);
	}
	
		
//	/school/{schoolId}
	public Mono<ServerResponse> getSchoolDetailsById(ServerRequest serverRequest) {
		String schoolId= serverRequest.pathVariable("schoolId");
		Mono<SchoolDetails> responseMono= service.getSchoolDetailsById(schoolId);
		return ServerResponse.ok() 
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMono, School.class);
	}
	
//	/school/{schoolId}/class/{classId}
	public Mono<ServerResponse> getClassDetailsBySchoolAndClassId(ServerRequest serverRequest) {
		String schoolId= serverRequest.pathVariable("schoolId");
		String classId= serverRequest.pathVariable("classId");
		Mono<ClassDetails> responseMono= service.getClassDetailsBySchoolAndClassId(schoolId, classId);
		return ServerResponse.ok() 
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMono, School.class);
	}
	
//	/user
	public Mono<ServerResponse> getAllUserDetails(ServerRequest serverRequest) {
		Flux<UserDetails> responseMono= service.getAllUserDetails();
		return ServerResponse.ok() 
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMono, School.class);
	}
	
//	/user/{userId}
	public Mono<ServerResponse> getUserDetailsById(ServerRequest serverRequest) {
		String userId= serverRequest.pathVariable("userId");
		Mono<UserDetails> responseMono= service.getUserDetailsById(userId);
		return ServerResponse.ok() 
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMono, School.class);
	}
	
//	/user/{schoolId}/teachers
	public Mono<ServerResponse> getTeachersBySchoolId(ServerRequest serverRequest) {
		String schoolId= serverRequest.pathVariable("schoolId");
		Flux<UserDetails> responseMono= service.getTeachersBySchoolId(schoolId);
		return ServerResponse.ok() 
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMono, School.class);
	}
//	/user/{schoolId}/students
	public Mono<ServerResponse> getStudentsBySchoolId(ServerRequest serverRequest) {
		String schoolId= serverRequest.pathVariable("schoolId");
		Flux<UserDetails> responseMono= service.getStudentsBySchoolId(schoolId);
		return ServerResponse.ok() 
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMono, School.class);
	}

}
