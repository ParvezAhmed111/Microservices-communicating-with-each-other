package com.project.servicesmerge.models;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.project.service2.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ClassDetails {
	@Id
	private String classId;
	private String className;
	private int studentStrength;
	private String userId;
	private String schoolId;
	
	private List<User> usersList;
}
