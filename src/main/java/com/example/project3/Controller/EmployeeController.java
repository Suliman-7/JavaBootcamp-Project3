package com.example.project3.Controller;


import com.example.project3.DTO.EmployeeDTO;
import com.example.project3.Model.Employee;
import com.example.project3.Model.User;
import com.example.project3.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor


public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get-all-employees")
    public ResponseEntity getAllEmployee(){
        return ResponseEntity.status(200).body(employeeService.getAllEmployees());
    }

    @PostMapping("/add-employee")
    public ResponseEntity addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        employeeService.registerEmployee(employeeDTO);
        return ResponseEntity.status(200).body("Employee Added");
    }

    @PutMapping("/update-employee")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user , @Valid @RequestBody EmployeeDTO employeeDTO){
        employeeService.updateEmployee(user.getId() ,employeeDTO);
        return ResponseEntity.status(200).body("Employee Updated");
    }


    @DeleteMapping("/delete-employee/{id}")
    public ResponseEntity deleteEmployee( @AuthenticationPrincipal User user , @PathVariable int id){
        employeeService.deleteEmployee(user.getId(),id);
        return ResponseEntity.status(200).body("Employee Deleted");
    }
}
