package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class EmployeeDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Double yearlySalary;

    private Double taxAmount;

    private Double cessAmount;
}
