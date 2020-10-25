package com.kocfinans.creditscore.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private long tc_id_number;
    private String fullname;
    private double monthly_salary;
    private String phone_number;

}
