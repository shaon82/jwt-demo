package com.shaon.jwtdemo.service;

import com.shaon.jwtdemo.model.EmployeeDtoResponse;
import com.shaon.jwtdemo.model.EmployeeRequestModel;
import com.shaon.jwtdemo.model.EmployeeResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {
    EmployeeDtoResponse findEmployeeById(Long id);

    EmployeeDtoResponse findEmployeeByEmail(String email);

    EmployeeResponseModel save(EmployeeRequestModel requestModel);

    List<EmployeeDtoResponse> findAll();
}
