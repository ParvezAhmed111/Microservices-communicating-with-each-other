package com.project.servicesmerge.routers;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.project.servicesmerge.handler.Handler;
import com.project.servicesmerge.models.ClassDetails;
import com.project.servicesmerge.models.SchoolDetails;
import com.project.servicesmerge.models.UserDetails;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@Configuration
public class ServicesMergeRouter {
	
	
	@RouterOperations({
		@RouterOperation(
				path = "/schools",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = Handler.class,
				beanMethod = "getAllSchoolDetails",
				operation = @Operation(
						operationId = "getAllSchoolDetails",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "School details fetched successfully",
										content = @Content(schema = @Schema(
												implementation = SchoolDetails.class
										))
								)
						}
				)
				
		),
		@RouterOperation(
				path = "/school/{schoolId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = Handler.class,
				beanMethod = "getSchoolDetailsById",
				operation = @Operation(
						operationId = "getSchoolDetailsById",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "School detail by id fetched successfully",
										content = @Content(schema = @Schema(
												implementation = SchoolDetails.class
										))
								),
								@ApiResponse(
										responseCode = "404",
										description = "School detail not found with given id"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "schoolId")
						}
				)
				
		),
		@RouterOperation(
				path = "/school/{schoolId}/class/{classId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = Handler.class,
				beanMethod = "getClassDetailsBySchoolAndClassId",
				operation = @Operation(
						operationId = "getClassDetailsBySchoolAndClassId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Class detail by school and class id fetched successfully",
										content = @Content(schema = @Schema(
												implementation = ClassDetails.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Class detail not found with given school and class id"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "schoolId"),
								@Parameter(in = ParameterIn.PATH, name = "classId")
						}
				)
			
			),
		@RouterOperation(
				path = "/users",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = Handler.class,
				beanMethod = "getAllUserDetails",
				operation = @Operation(
						operationId = "getAllUserDetails",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "User details fetched successfully",
										content = @Content(schema = @Schema(
												implementation = UserDetails.class
										))
								)
						}
				)
				
		),
		@RouterOperation(
				path = "/user/{userId}",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = Handler.class,
				beanMethod = "getUserDetailsById",
				operation = @Operation(
						operationId = "getUserDetailsById",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "User detail by user id fetched successfully",
										content = @Content(schema = @Schema(
												implementation = UserDetails.class
										))
								),
								@ApiResponse(
										responseCode = "404",
										description = "User detail not found with given user id"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "userId")
						}
				)		
		),
		@RouterOperation(
				path = "/user/{schoolId}/teachers",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = Handler.class,
				beanMethod = "getTeachersBySchoolId",
				operation = @Operation(
						operationId = "getTeachersBySchoolId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Teacher detail by school id fetched successfully",
										content = @Content(schema = @Schema(
												implementation = UserDetails.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Teacher detail not found with given school id"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "schoolId")
						}
					)		
			),
		@RouterOperation(
				path = "/user/{schoolId}/students",
				produces = {MediaType.APPLICATION_JSON_VALUE},
				method = RequestMethod.GET,
				beanClass = Handler.class,
				beanMethod = "getStudentsBySchoolId",
				operation = @Operation(
						operationId = "getStudentsBySchoolId",
						responses = {
								@ApiResponse(
										responseCode = "200",
										description = "Student detail by school id fetched successfully",
										content = @Content(schema = @Schema(
												implementation = UserDetails.class
												))
										),
								@ApiResponse(
										responseCode = "404",
										description = "Student detail not found with given school id"
										)
						},
						parameters = {
								@Parameter(in = ParameterIn.PATH, name = "schoolId")
						}
					)		
			)
		
	})
	
	 
		
	@Bean 
	public RouterFunction<ServerResponse> schoolAPIs(Handler handler){
		return route(GET("/schools"), handler :: getAllSchoolDetails)
			.andRoute(GET("/school/{schoolId}"), handler :: getSchoolDetailsById)
			.andRoute(GET("/school/{schoolId}/class/{classId}"), handler :: getClassDetailsBySchoolAndClassId)
			.andRoute(GET("/users"), handler :: getAllUserDetails)
			.andRoute(GET("/user/{userId}"), handler :: getUserDetailsById)
			.andRoute(GET("/user/{schoolId}/teachers"), handler :: getTeachersBySchoolId)
			.andRoute(GET("/user/{schoolId}/students"), handler :: getStudentsBySchoolId);
	}

}
