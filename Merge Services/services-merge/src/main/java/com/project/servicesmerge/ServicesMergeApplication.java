package com.project.servicesmerge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * @author parvez
 *
 */
@SpringBootApplication
@OpenAPIDefinition(info=@Info(
		title="Magic EdTech Spring-Webflux Project",
		version = "1.0",
		description="communicating the school and user services"
))
public class ServicesMergeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicesMergeApplication.class, args);
	}
}
