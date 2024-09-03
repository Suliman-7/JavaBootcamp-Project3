package com.example.project3.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    private Integer userId ;

    @NotEmpty(message = "user name should be not empty")
    @Size(min = 4 , max = 10 , message = "user name length should be at least 4 characters and at most 10 characters")
    private String username;

    @NotEmpty(message = "password should be not empty")
    @Size(min = 6 ,  message = "password length must be 6 characters")
    private String password;

    @NotEmpty(message = "name should be not empty")
    @Size(min = 2 , max = 20 , message = "name length should be at least 2 characters and at most 20 characters")
    private String name;

    @Email
    @NotEmpty(message = "email should be not empty")
    private String email;

    @NotEmpty(message = "role should be not empty")
    @Pattern(regexp = "^(CUSTOMER|ADMIN|EMPLOYEE)$")
    private String role;

    @NotEmpty(message = "customer phone number should be not empty")
    // @Pattern(regexp = "^05\\d{10}$\n")
    private String phoneNumber;
}
