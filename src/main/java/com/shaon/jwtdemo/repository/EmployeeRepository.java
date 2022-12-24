package com.shaon.jwtdemo.repository;

import com.shaon.jwtdemo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByEmail(String email);

    Employee findEmployeeById(Long id);
}
