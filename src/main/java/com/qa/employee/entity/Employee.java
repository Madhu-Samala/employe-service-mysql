package com.qa.employee.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "emp_details")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private int id;
	
	
	@NotNull
	@Size(min = 2, max = 20, message = "name must be between 2 and 20 characters only")
	@Pattern(regexp = "^[A-Za-z0-9]*", message = "invalid name, must contain only alphanumeric")
	@Column(name = "emp_name")
	private String name;
	
	
	
	@NotNull
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$", message = "Invalid email")
	@Column(name = "emp_email")
	private String email;
	
	
	@NotNull	
	@Pattern(regexp = "^(6|7|8|9)\\d{9}$", message = "invalid mobile number")
	@Column(name = "emp_contactno")
	private String contactno;
	
	
	@NotNull
	@Min(0)	
	@Column(name = "emp_salary")
	private double salary;
	
	@Column(name = "emp_age")
	private int age;
	
	@Column(name = "emp_dept")
	private String department;
	
	
	

}