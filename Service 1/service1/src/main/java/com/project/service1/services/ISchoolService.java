package com.project.service1.services;

import com.project.service1.models.School;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISchoolService {
	Mono<School> addSchool(School school);
	Mono<School> getSchoolById(String id);
	Flux<School> getAllSchools();
	Mono<School> updateSchool(String id, School school);
	Mono<Void> deleteSchoolById(String id);	
}
