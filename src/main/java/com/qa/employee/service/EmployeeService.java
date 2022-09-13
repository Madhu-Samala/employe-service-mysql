package com.qa.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.employee.entity.Employee;
import com.qa.employee.exception.EmployeeAlreadyExistsException;
import com.qa.employee.exception.EmployeeNotFoundException;
import com.qa.employee.repository.EmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {
	
	@Autowired
	EmployeeRepository empRepository;

	@Override
	public Employee saveEmployee(Employee employee) throws EmployeeAlreadyExistsException{
		/*
		 * 1. Check whether employee already exists with the name
		 * 2. If yes, throw EmployeeAlreadyExistsException
		 * 3. If no, save the employee object into the database
		 * 4. Return the saved employee	
		 */
		
		Optional<Employee> findByName = empRepository.findByName(employee.getName());
		if(findByName.isPresent())
				throw new EmployeeAlreadyExistsException();
		else
			return empRepository.save(employee);
	
	}

	@Override
	public List<Employee> getAllEmployees() {
		
		return empRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(int id) throws EmployeeNotFoundException {
		Optional<Employee> findById = empRepository.findById(id);
		if(!findById.isPresent())
			throw new EmployeeNotFoundException();
		else
			return findById.get();
	}

	@Override
	public Employee updateEmployee(Employee employee) throws EmployeeNotFoundException{
		Optional<Employee> findById = empRepository.findById(employee.getId());
		
		if(!findById.isPresent())
			throw new EmployeeNotFoundException();
		else {
				Employee existingEmp = findById.get(); 
				existingEmp.setEmail(employee.getEmail());			
			return empRepository.saveAndFlush(existingEmp);
		}
	}

	@Override
	public boolean deleteEmployeeById(int id) throws EmployeeNotFoundException{
		boolean status = false;
		Optional<Employee> findById = empRepository.findById(id);
		if(!findById.isPresent())
			throw new EmployeeNotFoundException();
		else {
			empRepository.delete(findById.get());
			status = true;
			}
		
		return status;
	}

}
