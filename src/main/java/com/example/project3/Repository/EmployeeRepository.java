package com.example.project3.Repository;

import com.example.project3.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findEmployeeById(int id);

}
