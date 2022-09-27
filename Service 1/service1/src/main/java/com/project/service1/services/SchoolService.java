package com.project.service1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.service1.models.School;
import com.project.service1.repository.SchoolRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SchoolService implements ISchoolService{
	
	
	
	@Autowired
	SchoolRepository schoolRepository;

	@Override
	public Mono<School> addSchool(School school) {
		return schoolRepository.save(school);
	}
  
	@Override
	public Mono<School> getSchoolById(String id) {
		return schoolRepository.findById(id);
	}

	@Override
	public Flux<School> getAllSchools() {
		return schoolRepository.findAll();
	}

	@Override
	public Mono<School> updateSchool(String id, School school) {
		Mono<School> existingSchool= schoolRepository.findById(id);
		return existingSchool.flatMap(r->{
									r.setSchoolId(id);
									r.setSchoolName(school.getSchoolName());
									return schoolRepository.save(r);
								}); 

	}

	@Override
	public Mono<Void> deleteSchoolById(String id) {
		return schoolRepository.deleteById(id);
	}

}
