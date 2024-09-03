package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.DTO.CustomerDTO;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    public List<User> getAllUsers() {
        return authRepository.findAll();
    }








    public void deleteUser(int id) {
        User user = authRepository.findUserById(id);
        if(user==null){
            throw new ApiException("wrong username or password ");
        }
        authRepository.delete(user);
    }
}
