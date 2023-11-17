package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO;
import com.example.demo.model.Employee;

import java.util.List;

public interface EmployeeService {

    public List<EmployeeDTO> getEmployees();

    public Employee save(Employee emp);
}
