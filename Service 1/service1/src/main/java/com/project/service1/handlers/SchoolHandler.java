package com.project.service1.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.service1.models.School;
import com.project.service1.services.ISchoolService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SchoolHandler {
	
	@Autowired
	private ISchoolService schoolService;

	private Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	
	//POST
	public Mono<ServerResponse> addSchool(ServerRequest request) {
		Mono<School> schoolMono= request.bodyToMono(School.class);
		
		return schoolMono.flatMap(r-> ServerResponse.ok()
								.contentType(MediaType.APPLICATION_JSON)
								.body(schoolService.addSchool(r), School.class));
	}
	
	//GET
	public Mono<ServerResponse> getAllSchools(ServerRequest request) {
		Flux<School> schoolFlux= schoolService.getAllSchools();
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(schoolFlux, School.class);
	}

	public Mono<ServerResponse> getSchoolById(ServerRequest request) {
		String id= request.pathVariable("id");
		Mono<School> schoolMono= schoolService.getSchoolById(id);
		
		return schoolMono.flatMap(r-> ServerResponse.ok()
								.contentType(MediaType.APPLICATION_JSON)
								.body(schoolMono, School.class))
				.switchIfEmpty(notFound);	
	}
	
	//PUT
	public Mono<ServerResponse> updateSchool(ServerRequest serverRequest){
		String id= serverRequest.pathVariable("id");
		Mono<School> schoolMono= serverRequest.bodyToMono(School.class);	

		return schoolMono.flatMap(r -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(schoolService.updateSchool(id, r), School.class));
	}
	
	//DELETE
	public Mono<ServerResponse> deleteSchoolById(ServerRequest serverRequest){
		String id= serverRequest.pathVariable("id");
		Mono<Void> schoolMono= schoolService.deleteSchoolById(id);
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(schoolMono, School.class);
	}
}
