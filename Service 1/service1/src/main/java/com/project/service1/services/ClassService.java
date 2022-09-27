package com.project.service1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.service1.models.Class;
import com.project.service1.repository.ClassRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClassService implements IClassService {
	
	@Autowired
	ClassRepository classRepository;

	@Override
	public Mono<Class> addClass(Class class1) {
		return classRepository.save(class1);
	}

	@Override
	public Mono<Class> getClassById(String id) {
		return classRepository.findById(id);
	}

	@Override
	public Flux<Class> getAllClasses() {
		return classRepository.findAll();
	}

	@Override
	public Mono<Class> updateClass(String id, Class class1) {
		Mono<Class> existingClass= classRepository.findById(id);
		return existingClass.flatMap(r->{
									r.setClassId(id);
									r.setClassName(class1.getClassName());
									r.setStudentStrength(class1.getStudentStrength());
									r.setUserId(class1.getUserId());
									return classRepository.save(r);
								});
	}

	@Override
	public Mono<Void> deleteClassById(String id) {
		return classRepository.deleteById(id);
	}

}
