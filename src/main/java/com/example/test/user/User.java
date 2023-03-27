package com.example.test.user;

import java.util.Date;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

public class User {

	private Integer id;
	
	@Size(min=2, message="name should have atleast 2 charaters")
	private String name;
	
	@Past
	private Date dateOfBirth;
	
	
	public User(Integer id, String name, Date dateOfBirth) {
		super();
		
		this.id = id;
		this.name = name;
		this.dateOfBirth =  dateOfBirth;
		
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	
}
