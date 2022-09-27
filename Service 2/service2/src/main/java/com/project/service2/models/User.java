package com.project.service2.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Document("User Data")
public class User{
	@Id
	private String userId;

	@NotNull(message = "Name cannot be null")
	private String userName;
	private String userRole;
	@NotBlank(message = "Standard cannot be blank")
	private String classId;
}
