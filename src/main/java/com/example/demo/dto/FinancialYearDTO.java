package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
public class FinancialYearDTO {

    private Date startDate;
    private Date endDate;
}
