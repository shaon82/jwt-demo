package com.shaon.jwtdemo.controller;


import com.shaon.jwtdemo.auth.SecurityConstants;
import com.shaon.jwtdemo.model.EmployeeDtoResponse;
import com.shaon.jwtdemo.model.EmployeeRequestModel;
import com.shaon.jwtdemo.model.EmployeeResponseModel;
import com.shaon.jwtdemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/jwt-demo")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/save")
    public EmployeeResponseModel save(@RequestBody EmployeeRequestModel requestModel){
        EmployeeResponseModel responseModel = employeeService.save(requestModel);
        return responseModel;
    }


    @GetMapping("/find")
    public EmployeeDtoResponse getEmployee(@RequestParam("id") Long id, HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING).replace(SecurityConstants.TOKEN_PREFIX,"");
        EmployeeDtoResponse employeeDtoResponse = employeeService.findEmployeeById(id);
        System.out.println(getPayloadValue(token));
        return employeeDtoResponse;
    }

    @GetMapping("/find-all")
    public List<EmployeeDtoResponse> findAll(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING).replace(SecurityConstants.TOKEN_PREFIX,"");
        System.out.println(getPayloadValue(token));
        return employeeService.findAll();
    }

    public String getPayloadValue(String token){
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        return payload;
    }






}
