package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {


	@Autowired  private EmployeeService employeeService;

	@GetMapping("/list")
	public List<EmployeeDTO> getEmployees() {
		return employeeService.getEmployees();
	}
	
	@PostMapping(value = "/add")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
	

}
