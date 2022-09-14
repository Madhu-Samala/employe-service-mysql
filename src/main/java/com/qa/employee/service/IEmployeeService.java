package com.qa.employee.service;

import java.util.List;

import com.qa.employee.dto.EmployeeDto;
import com.qa.employee.entity.Employee;
import com.qa.employee.exception.EmployeeAlreadyExistsException;
import com.qa.employee.exception.EmployeeNotFoundException;

public interface IEmployeeService {
	//CRUD operations
	public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException;
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(int id) throws EmployeeNotFoundException;
	public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException;
	public boolean deleteEmployeeById(int id) throws EmployeeNotFoundException;
	
	public List<Employee> findEmployeesByAgeAndDept(int age, String dept);
	
	public Double findTotalSalariesOfAllEmployees();
	public Employee updateEmployeeDetails(Employee employee);
	
	public List<EmployeeDto> findEmployeeDetailsWithDto();

}
