package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee")
@Setter
@Getter
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="firstName")
	private String firstName;
	
    @Column(name="lastName")
	private String lastName;

	@Column(name="empId")
	private String empId;

	@Column(name="email")
	private String email;

	@Column(name="phoneNumber")
	private String phoneNumber;

	@Column(name="doj")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date doj;

	@Column(name="salary")
	private Double salary;

}
