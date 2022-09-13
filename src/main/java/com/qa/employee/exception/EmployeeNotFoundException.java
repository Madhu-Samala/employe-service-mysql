package com.qa.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Employee doesn't Exists with this Id")
public class EmployeeNotFoundException extends Exception {

}
