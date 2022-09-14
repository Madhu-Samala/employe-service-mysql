package com.qa.employee.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.qa.employee.entity.Employee;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	
	Optional<Employee> findByName(String name);
	
	/* Native Query (SQL)
	 * select * from emp_details where emp_age > 27 and emp_dept = 'development'
	 */
	//Positional Parameters
	//@Query(value = "select * from emp_details where emp_age > ?1 and emp_dept = ?2", nativeQuery = true)
	//Named Parameters
	/*
	@Query(value = "select * from emp_details where emp_age > :age and emp_dept = :department", nativeQuery = true)
	List<Employee> findEmployeeByAgeAndDept(int age, String department);
	*/
	
	@Query(value = "select e from Employee e where e.age > :age and e.department = :department")
	List<Employee> findEmployeeByAgeAndDept(int age, String department);
	
	@Query(value = "select sum(emp_salary) from emp_details", nativeQuery = true)
	Double findTotalSalariesOfAllEmployees();
	
	/* update, delete (DML Query) */
	
	@Modifying
	@Query(value = "update Employee e set e.email=:email , e.contactno = :contactno where e.id = :id")
	int updateEmpDetails(int id,String email, String contactno);
}
