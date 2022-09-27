package com.project.service1.services;

import com.project.service1.models.Class;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClassService {
	Mono<Class> addClass(Class class1);
	Mono<Class> getClassById(String id);
	Flux<Class> getAllClasses();
	Mono<Class> updateClass(String id, Class class1);
	Mono<Void> deleteClassById(String id);
}
