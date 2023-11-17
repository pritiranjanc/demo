create database employeeDB;

use employeeDB;


create table employee
(
	id int primary key auto_increment,
	firstName varchar(50) not null,
	lastName varchar(50) not null,
	empId varchar(50) not null,
	email varchar(50) not null,
	phoneNumber varchar(50),
	doj date not null,
	salary double not null
);




