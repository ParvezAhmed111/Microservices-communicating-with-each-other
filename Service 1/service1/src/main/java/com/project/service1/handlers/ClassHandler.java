package com.project.service1.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.service1.models.Class;
import com.project.service1.services.IClassService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ClassHandler {
	
	@Autowired
	private IClassService classService;

	private Mono<ServerResponse> notFound = ServerResponse.notFound().build();
	
	//POST
	public Mono<ServerResponse> addClass(ServerRequest request) {
		Mono<Class> classMono= request.bodyToMono(Class.class);
		
		return classMono.flatMap(r-> ServerResponse.ok()
								.contentType(MediaType.APPLICATION_JSON)
								.body(classService.addClass(r), Class.class));
	}
	
	//GET
	public Mono<ServerResponse> getAllClasses(ServerRequest request) {
		Flux<Class> classFlux= classService.getAllClasses();
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(classFlux, Class.class);
	}

	public Mono<ServerResponse> getClassById(ServerRequest request) {
		String id= request.pathVariable("id");
		Mono<Class> classMono= classService.getClassById(id);
		
		return classMono.flatMap(r-> ServerResponse.ok()
								.contentType(MediaType.APPLICATION_JSON)
								.body(classMono, Class.class))
				.switchIfEmpty(notFound);	
	}
	
	//PUT
	public Mono<ServerResponse> updateClass(ServerRequest serverRequest){
		String id= serverRequest.pathVariable("id");
		Mono<Class> classMono= serverRequest.bodyToMono(Class.class);	

		return classMono.flatMap(r -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(classService.updateClass(id, r), Class.class));
	}
	
	//DELETE
	public Mono<ServerResponse> deleteClassById(ServerRequest serverRequest){
		String id= serverRequest.pathVariable("id");
		Mono<Void> classMono= classService.deleteClassById(id);
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(classMono, Class.class);
	}

}
