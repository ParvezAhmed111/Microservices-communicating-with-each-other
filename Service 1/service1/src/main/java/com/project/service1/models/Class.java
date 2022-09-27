package com.project.service1.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

@Document("Class Data")
public class Class {
	@Id
	private String classId;
	private String className;
	private int studentStrength;
	private String userId; 
	private String schoolId;
}
