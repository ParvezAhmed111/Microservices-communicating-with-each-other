package com.project.servicesmerge.models;

import org.springframework.data.annotation.Id;

import com.project.service1.models.Class;
import com.project.service1.models.School;

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
public class UserDetails{
	@Id
	private String userId;
	private String userName;
	private String userRole;
	private String classId;
	
	private Class class1;
	private School school;
}
