package com.shaon.jwtdemo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaon.jwtdemo.model.EmployeeDtoResponse;
import com.shaon.jwtdemo.model.LoginRequestModel;
import com.shaon.jwtdemo.service.EmployeeService;
import com.shaon.jwtdemo.utility.SpringApplicationContext;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestModel loginRequestModel = new ObjectMapper().readValue(request.getInputStream(), LoginRequestModel.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestModel.getEmail(), loginRequestModel.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = ((User)authResult.getPrincipal()).getUsername();
        EmployeeService employeeService = (EmployeeService) SpringApplicationContext.getBean("employeeServiceImpl");
        EmployeeDtoResponse employeeDtoResponse = employeeService.findEmployeeByEmail(userName);

        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS256,SecurityConstants.TOKEN_SECRET).claim("name",employeeDtoResponse.getName())
                .claim("id",employeeDtoResponse.getId()).claim("validated", "true").compact();




        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        response.addHeader("empId",employeeDtoResponse.getId().toString());
    }

}
