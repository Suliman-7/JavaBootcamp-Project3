package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor


public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<User> getAllEmployees() {
        List<User> AllEmployees = new ArrayList<>();
        for(User user : authRepository.findAll()){
            if(user.getRole().equals("EMPLOYEE")){
                AllEmployees.add(user);
            }
        }
        return AllEmployees;
    }



    public void registerEmployee(EmployeeDTO employeeDTO) {

        if(!employeeDTO.getRole().equalsIgnoreCase("EMPLOYEE")){
            throw new ApiException("invalid role");
        }
        String hash = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        employeeDTO.setPassword(hash);

        User user = new User(null,employeeDTO.getUsername(),hash,employeeDTO.getName(),employeeDTO.getEmail(),employeeDTO.getRole(),null,null);
        authRepository.save(user);
        Employee employee = new Employee(user.getId(),employeeDTO.getPosition(),employeeDTO.getSalary(),user);
        authRepository.save(user);
        employeeRepository.save(employee);
        authRepository.save(user);
    }


    public void updateEmployee(int userId , EmployeeDTO employeeDTO) {
        User user = authRepository.findUserById(userId);
        Employee employee = user.getEmployee();

        if(employee==null){
            throw new ApiException("employee not found");
        }

        if(user.getId()!=employeeDTO.getUserId()){
            throw new ApiException("employee id not match");
        }


        user.setUsername(employeeDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        employeeDTO.setPassword(hash);
        user.setPassword(hash);
        user.setName(employeeDTO.getName());
        user.setEmail(employeeDTO.getEmail());
        user.setRole(employeeDTO.getRole());
        authRepository.save(user);
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());
        employeeRepository.save(employee);
    }


    public void deleteEmployee(int userId , int id) {
        User user = authRepository.findUserById(userId);
        Employee e = employeeRepository.findEmployeeById(id);

        if(e == null){
            throw new ApiException("employee not found");
        }

        if(user.getId()!=e.getId()) {
            throw new ApiException("employee id not match");
        }


        e.setUser(null);
        authRepository.delete(user);
    }

}
