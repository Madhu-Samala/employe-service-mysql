package com.qa.employee.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qa.employee.entity.Employee;
import com.qa.employee.exception.EmployeeAlreadyExistsException;
import com.qa.employee.exception.EmployeeNotFoundException;
import com.qa.employee.service.EmployeeService;

@RestController
@RequestMapping("api/v1/employee-service")
public class EmployeeController {

	@Autowired
	EmployeeService empService;

	/*
	 * It is the responsible for sending the response to the client converting java
	 * objects to json by default along with the status code
	 * 
	 * 
	 */
	ResponseEntity<?> responseEntity;

	@PostMapping("/employee")
	public ResponseEntity<?> saveEmployee(@Valid @RequestBody Employee employee) throws EmployeeAlreadyExistsException {
		Employee createdEmployee;
		try {
			createdEmployee = this.empService.saveEmployee(employee);
		} catch (EmployeeAlreadyExistsException e) {
			throw e;
		}
		responseEntity = new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
		return responseEntity;
	}

	@GetMapping("/employee")
	public ResponseEntity<?> getAllEmployees() {
		return new ResponseEntity<>(this.empService.getAllEmployees(), HttpStatus.OK);
	}

	// employee/3 -> Path Variable

	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable("id") int id) throws EmployeeNotFoundException {
		Employee employee;
		try {
			employee = this.empService.getEmployeeById(id);
		} catch (EmployeeNotFoundException e) {
			throw e;
		}
		responseEntity = new ResponseEntity<>(employee, HttpStatus.OK);
		return responseEntity;
	}

	// employee?id=3 -> RequestParameter
	@DeleteMapping("/employee")
	public ResponseEntity<?> deleteEmployeeById(@RequestParam("id") int id) throws EmployeeNotFoundException {
		boolean status;
		try {
			status = this.empService.deleteEmployeeById(id);
			responseEntity = new ResponseEntity<>("Employee Deleted Successfully !!!", HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			throw e;
		} catch(Exception e) {
			responseEntity = new ResponseEntity<>("Some internal error occured , please try again !!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@PutMapping("/employee")
	public ResponseEntity<?> updateEmployee(@RequestBody Employee employee) throws EmployeeNotFoundException {
		try {
			responseEntity = new ResponseEntity<>(empService.updateEmployee(employee), HttpStatus.OK);
		} catch (EmployeeNotFoundException e) {
			throw e;
		}
		return responseEntity;
	}

	@GetMapping("/employee/age/{age}/dept/{dept}")
	public ResponseEntity<?> getEmployeeByAgeAndDept(@PathVariable("age") int age, @PathVariable("dept") String dept) {
		return new ResponseEntity<>(empService.findEmployeesByAgeAndDept(age, dept), HttpStatus.OK);
	}
	
	@GetMapping("/employee/salary")
	public ResponseEntity<?> getTotalSalariesOfAllEmployees(){
		return new ResponseEntity<>(empService.findTotalSalariesOfAllEmployees(),HttpStatus.OK);
	}
	
	@PutMapping("/employee/updatedetails")
	public ResponseEntity<?> updateEmpDetails(@RequestBody Employee employee){
		return new ResponseEntity<>(empService.updateEmployeeDetails(employee),HttpStatus.OK);
	}

	
	@GetMapping("/employee/dto-details")
	public ResponseEntity<?> getEmpDtoDetails(){
		return new ResponseEntity<>(empService.findEmployeeDetailsWithDto(),HttpStatus.OK);
	}
}
