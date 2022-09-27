package com.project.servicesmerge.services;

import com.project.servicesmerge.models.ClassDetails;
import com.project.servicesmerge.models.SchoolDetails;
import com.project.servicesmerge.models.UserDetails;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProjectService {
	Flux<SchoolDetails> getAllSchoolDetails();
	Mono<SchoolDetails> getSchoolDetailsById(String schoolId);
	Mono<ClassDetails> getClassDetailsBySchoolAndClassId(String schoolId, String classId);
	Flux<UserDetails> getAllUserDetails();
	Mono<UserDetails> getUserDetailsById(String userId);
	Flux<UserDetails> getTeachersBySchoolId(String schoolId);
	Flux<UserDetails> getStudentsBySchoolId(String schoolId);
}
