package com.qa.employee.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.employee.dto.EmployeeDto;
import com.qa.employee.entity.Employee;
import com.qa.employee.exception.EmployeeAlreadyExistsException;
import com.qa.employee.exception.EmployeeNotFoundException;
import com.qa.employee.repository.EmployeeRepository;

@Service
public class EmployeeService implements IEmployeeService {
	
	@Autowired
	EmployeeRepository empRepository;
	
	@Autowired
	ModelMapper modelMapper;

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

	@Override
	public List<Employee> findEmployeesByAgeAndDept(int age, String dept) {		
		return empRepository.findEmployeeByAgeAndDept(age, dept);
	}

	@Override
	public Double findTotalSalariesOfAllEmployees() {
		return empRepository.findTotalSalariesOfAllEmployees();
	}

	@Override
	public Employee updateEmployeeDetails(Employee employee) {
		int rows = empRepository.updateEmpDetails(employee.getId(), employee.getEmail(), employee.getContactno());
		System.out.println(rows);
		
		return empRepository.findById(employee.getId()).get();
	}

	@Override
	public List<EmployeeDto> findEmployeeDetailsWithDto() {
		return this.empRepository.findAll().stream().map(this::mapToEmployeeDto).collect(Collectors.toList());
	}
	
	private EmployeeDto mapToEmployeeDto(Employee employee) {
		return this.modelMapper.map(employee, EmployeeDto.class);
	}

}
