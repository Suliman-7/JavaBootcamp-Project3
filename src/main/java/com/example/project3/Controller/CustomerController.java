package com.example.project3.Controller;

import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor

public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get-all-customers")
    public ResponseEntity getAllCustomers(){
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    @PostMapping("/register-customer")
    public ResponseEntity registerCustomer(@Valid @RequestBody CustomerDTO customerDTO){
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.status(200).body("Customer Added");
    }

    @PutMapping("/update-customer")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user , @Valid @RequestBody CustomerDTO customerDTO){
        customerService.updateCustomer(user.getId(),customerDTO);
        return ResponseEntity.status(200).body("Customer Updated");
    }

    @DeleteMapping("/delete-customer/{id}")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user , @PathVariable int id){

        customerService.deleteCustomer(user.getId(),id);
        return ResponseEntity.status(200).body("Customer Deleted");
    }


}
