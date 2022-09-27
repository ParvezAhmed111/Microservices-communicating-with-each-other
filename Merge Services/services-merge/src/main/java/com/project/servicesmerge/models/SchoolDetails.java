package com.project.servicesmerge.models;

import java.util.List;

import org.springframework.data.annotation.Id;

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
public class SchoolDetails {
	@Id
	private String schoolId;
	private String schoolName;
	
	private List<ClassDetails> classList; 
}