package com.project.servicesmerge.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.project.service1.models.Class;
import com.project.service1.models.School;
import com.project.service2.models.User;
import com.project.servicesmerge.models.ClassDetails;
import com.project.servicesmerge.models.SchoolDetails;
import com.project.servicesmerge.models.UserDetails;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectService implements IProjectService{
	
	// School and Class base URL
	public static final String SC_URL = "http://localhost:8081/";		
	// User base URL
	public static final String USER_URL = "http://localhost:8082/";	

	WebClient webClient = WebClient.create();
	
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	
//	/schools :- to fetch details for all schools includes class, 
//								student as well as teacher details, 
//								add query param on school name
	@Override
	public Flux<SchoolDetails> getAllSchoolDetails(){
		return webClient.get() 
				.uri(SC_URL+"/schools")
				.retrieve()
				.bodyToFlux(School.class)
				.flatMap(school-> getSchoolDetailsById(school.getSchoolId()));
	}
	
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	
	
	// /school/{schoolId} : fetch details for a particular school 
	@Override 
	public Mono<SchoolDetails> getSchoolDetailsById(String schoolId){
		return getSchoolById(schoolId).flatMap(school->{
			return getClassesBySchoolId(school)
					.flatMap(schoolObjectWithClassDetail->{
						return getUsersByClassId(schoolObjectWithClassDetail);
					});
		});		
	}
	
	private Mono<School> getSchoolById(String schoolId){
		return webClient.get()
				.uri(SC_URL+"school/"+schoolId)
				.retrieve()
				.bodyToMono(School.class);
	}


	private Mono<SchoolDetails> getClassesBySchoolId(School school){
		SchoolDetails schoolDetails= new SchoolDetails();
		return webClient.get()
						.uri(SC_URL+"classes") 
						.retrieve()
						.bodyToFlux(ClassDetails.class)
						.filter(class1-> class1.getSchoolId().equals(school.getSchoolId()))
						.collectList()
						.flatMap(list->{
							schoolDetails.setSchoolId(school.getSchoolId());
							schoolDetails.setSchoolName(school.getSchoolName());
							schoolDetails.setClassList(list);
							return Mono.just(schoolDetails);
							}); 
	}

	
	
	private Mono<SchoolDetails> getUsersByClassId(SchoolDetails schoolDetails){
		List<ClassDetails> classes= schoolDetails.getClassList();
		
		return Flux.fromIterable(classes).flatMap(classObj->{
			return getUsers(classObj.getUserId())
					.flatMap(users->setUsersInClassObj(classObj, users));
		}).collectList().flatMap(classesObj->{
			schoolDetails.setClassList(classesObj);	
			return Mono.just(schoolDetails);
		}); 
	}
		
	private Mono<ClassDetails> setUsersInClassObj(ClassDetails classObj, List<User>users){
		classObj.setUsersList(users);
		return Mono.just(classObj);
	}
	
	private Mono<List<User>> getUsers(String classId){
		return webClient.get()
				.uri(USER_URL+"users") 
				.retrieve()
				.bodyToFlux(User.class)
				.filter(user-> user.getClassId().equals(classId))
				.collectList();
	}
	
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	
//	/school/{schoolId}/class/{classId}: fetch details for a class in a school
	@Override
	public Mono<ClassDetails> getClassDetailsBySchoolAndClassId(String schoolId, String classId){
		return getSchoolById(schoolId)
				.flatMap(school->{
					return getClassByClassId(classId, schoolId)
							.flatMap(classDetail->getUsersByClassId(classDetail));
					});
	}
	
	
	private Mono<ClassDetails> getUsersByClassId(Class class1){
		ClassDetails classDetails= new ClassDetails();
		 return getUsers(class1.getClassId())
				 .flatMap(user->{
					 classDetails.setClassId(class1.getClassId());
					 classDetails.setClassName(class1.getClassName());
					 classDetails.setStudentStrength(class1.getStudentStrength());
					 classDetails.setUserId(class1.getUserId());
					 classDetails.setSchoolId(class1.getSchoolId());
					 setUsersInClassObj(classDetails, user);
					 return Mono.just(classDetails);
				 });
	}
	
	public Mono<Class> getClassByClassId(String classId, String schoolId) {
		return webClient.get()
				.uri(SC_URL+"class/"+classId)
				.retrieve()
				.bodyToMono(Class.class)
				.flatMap(class1->{
					if(class1.getSchoolId().equals(schoolId)) {
						return Mono.just(class1);
					}
					throw new IllegalArgumentException("Given Id not found."+ schoolId);
				});
	}
	
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

//	/users :- fetch all user details including school, class and teacher details
	@Override
	public Flux<UserDetails> getAllUserDetails(){
		return webClient.get() 
				.uri(USER_URL+"/users")
				.retrieve()
				.bodyToFlux(User.class)
				.flatMap(user-> getUserDetailsById(user.getUserId()));
	}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

	
//	/user/{userId} : fetch details of a specific user
	@Override
	public Mono<UserDetails> getUserDetailsById(String userId){
		return getUserById(userId).flatMap(user->{
			return getClassesByUserId(user);
		});		
	}
	private Mono<User> getUserById(String userId){
		return webClient.get()
				.uri(USER_URL+"user/"+userId)
				.retrieve()
				.bodyToMono(User.class);
	}
	
	private Mono<UserDetails> getClassesByUserId(User user){
		return webClient.get()
						.uri(SC_URL+"class/"+user.getClassId()) 
						.retrieve()
						.bodyToMono(Class.class)
						.flatMap(class1->{
							return getSchoolByClassId(class1, user);							
						});
	} 

	private Mono<UserDetails> getSchoolByClassId(Class class1, User user){
		UserDetails userDetails= new UserDetails();
		return webClient.get()
				.uri(SC_URL+"school/"+class1.getSchoolId()) 
				.retrieve()
				.bodyToMono(School.class)
				.flatMap(school->{
					userDetails.setUserId(user.getUserId());
					userDetails.setUserName(user.getUserName());
					userDetails.setUserRole(user.getUserRole());
					userDetails.setClassId(user.getClassId());
					userDetails.setClass1(class1);
					userDetails.setSchool(school);
					return Mono.just(userDetails);
				});
	}
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
	
//	/user/{schoolId}/teachers :- to fetch students’ details for a school including school details
	public Flux<UserDetails> getTeachersBySchoolId(String schoolId){
		return getAllUserDetails()
				.filterWhen(e-> getClassOfSchoolById(e.getClassId(), schoolId).hasElements())
				.filter(e-> e.getUserRole().equals("teacher"));
	}

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

//	/user/{schoolId}/students :- to fetch students’ details for a school including school details
	public Flux<UserDetails> getStudentsBySchoolId(String schoolId){
		return getAllUserDetails()
				.filterWhen(e-> getClassOfSchoolById(e.getClassId(), schoolId).hasElements())
				.filter(e-> e.getUserRole().equals("student"));
	}

	
	private Flux<ClassDetails> getClassOfSchoolById(String classId, String schoolId){
		return webClient.get()
				.uri(SC_URL+"classes")
				.retrieve()
				.bodyToFlux(ClassDetails.class)
				.filter(e->e.getSchoolId().equals(schoolId))
				.filter(e->e.getClassId().equals(classId));		
	}	
}
