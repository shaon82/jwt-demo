package com.shaon.jwtdemo.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class EmployeeRequestModel {

    private String name;
    private String email;
    private String password;
}
