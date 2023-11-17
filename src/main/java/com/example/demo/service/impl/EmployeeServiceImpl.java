package com.example.demo.service.impl;

import com.example.demo.dao.EmployeeRepository;
import com.example.demo.dto.EmployeeDTO;
import com.example.demo.dto.FinancialYearDTO;
import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired  private EmployeeRepository employeeRepository;
    @Override
    public List<EmployeeDTO> getEmployees() {
        List<Employee> empList = employeeRepository.findAll();
        return empList.stream().map(this::getEmployeeDTO).collect(Collectors.toList());
    }

    private EmployeeDTO getEmployeeDTO(Employee emp){
        EmployeeDTO dto = new EmployeeDTO();
        BeanUtils.copyProperties(emp,dto);
        FinancialYearDTO finYear =  getCurrentFinancialYear();
        if(emp.getDoj().compareTo(finYear.getStartDate())<=0) {
            dto.setYearlySalary(emp.getSalary() * 12);
        }
        if(emp.getDoj().compareTo(finYear.getStartDate())>0) {
            LocalDate from = emp.getDoj().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate to = finYear.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            long monthsBetween = ChronoUnit.MONTHS.between(
                    from.withDayOfMonth(1),
                    to.withDayOfMonth(1));
            dto.setYearlySalary(emp.getSalary() * monthsBetween);
        }

        if(dto.getYearlySalary() < 250000){
            dto.setTaxAmount(0d);
        }
        else if(dto.getYearlySalary() >= 250000 && dto.getYearlySalary()<=500000){
            dto.setTaxAmount((dto.getYearlySalary()-250000*5/100));
        }
        else if(dto.getYearlySalary() > 500000 && dto.getYearlySalary()<=1000000){
            Double firstSlab = Double.valueOf(250000*5/100);
            Double secondSlab = (dto.getYearlySalary()-500000)*10/100;
            dto.setTaxAmount(firstSlab+secondSlab);
        }
        else if(dto.getYearlySalary() > 1000000){
            Double firstSlab = Double.valueOf(250000*5/100);
            Double secondSlab = Double.valueOf(500000*10/100);
            Double thirdSlab = (dto.getYearlySalary()-1000000)*20/100;
            dto.setTaxAmount(firstSlab+secondSlab+thirdSlab);
        }
        else if(dto.getYearlySalary() > 2500000){
            Double firstSlab = Double.valueOf(250000*5/100);
            Double secondSlab = Double.valueOf(500000*10/100);
            Double thirdSlab = (dto.getYearlySalary()-1000000)*20/100;
            dto.setTaxAmount(firstSlab+secondSlab+thirdSlab);
            dto.setCessAmount((dto.getYearlySalary()- 2500000)*2/100);
        }
        return dto;
    }

    private FinancialYearDTO getCurrentFinancialYear(){
        int CurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        int CurrentMonth = (Calendar.getInstance().get(Calendar.MONTH)+1);
        String financiyalYearFrom="";
        String financiyalYearTo="";
        FinancialYearDTO finYear = new FinancialYearDTO();
        if (CurrentMonth<4) {
            financiyalYearFrom="01-04-"+(CurrentYear-1);
            financiyalYearTo="31-03-"+(CurrentYear);

        } else {
            financiyalYearFrom="01-04-"+(CurrentYear);
            financiyalYearTo="31-03-"+(CurrentYear+1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateTime = LocalDate.parse(financiyalYearFrom, formatter);
        LocalDate dateTime1 = LocalDate.parse(financiyalYearTo, formatter);
        finYear.setStartDate(Date.from(dateTime.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        finYear.setEndDate(Date.from(dateTime1.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return finYear;
    }

    @Override
    public Employee save(Employee emp) {
        return employeeRepository.save(emp);
    }
}
