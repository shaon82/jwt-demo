package com.shaon.jwtdemo.service;

import com.shaon.jwtdemo.model.Employee;
import com.shaon.jwtdemo.model.EmployeeDtoResponse;
import com.shaon.jwtdemo.model.EmployeeRequestModel;
import com.shaon.jwtdemo.model.EmployeeResponseModel;
import com.shaon.jwtdemo.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public EmployeeDtoResponse findEmployeeById(Long id) {
        EmployeeDtoResponse employeeDtoResponse = new EmployeeDtoResponse();
        Employee employee = employeeRepository.findEmployeeById(id);
        BeanUtils.copyProperties(employee, employeeDtoResponse);
        return employeeDtoResponse;
    }

    @Override
    public EmployeeDtoResponse findEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null) throw new UsernameNotFoundException(email);
        EmployeeDtoResponse response = new EmployeeDtoResponse();
        BeanUtils.copyProperties(employee, response);
        return response;
    }

    @Override
    public EmployeeResponseModel save(EmployeeRequestModel requestModel) {
        Employee employee = new Employee();
        EmployeeResponseModel responseModel = new EmployeeResponseModel();
        BeanUtils.copyProperties(requestModel, employee);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        Employee employee1 = employeeRepository.save(employee);
        BeanUtils.copyProperties(employee1, responseModel);
        return responseModel;
    }

    @Override
    public List<EmployeeDtoResponse> findAll() {
        List<EmployeeDtoResponse> employeeDtoResponses = new ArrayList<>();
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employee: employees){
            EmployeeDtoResponse employeeDtoResponse = new EmployeeDtoResponse();
            BeanUtils.copyProperties(employee, employeeDtoResponse);
            employeeDtoResponses.add(employeeDtoResponse);

        }
        return employeeDtoResponses;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findEmployeeByEmail(username);
        if (employee == null) throw new UsernameNotFoundException(username);
        return new User(employee.getEmail(), employee.getPassword(), new ArrayList<>());
    }


}
