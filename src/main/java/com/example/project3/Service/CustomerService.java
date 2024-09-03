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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor


public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<User> getAllCustomers() {
        List<User> AllCustomers = new ArrayList<>();
        for(User user : authRepository.findAll()){
            if(user.getRole().equals("CUSTOMER")){
                AllCustomers.add(user);
            }
        }
        return AllCustomers;
    }

    public void registerCustomer(CustomerDTO customerDTO) {

        if(!customerDTO.getRole().equalsIgnoreCase("CUSTOMER")){
            throw new ApiException("invalid role");
        }

        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        customerDTO.setPassword(hash);

        User user = new User(null,customerDTO.getUsername(),hash,customerDTO.getName(),customerDTO.getEmail(),customerDTO.getRole(),null,null);
        authRepository.save(user);
        Customer customer = new Customer(user.getId(),customerDTO.getPhoneNumber(),user,null);
        user.setCustomer(customer);
        authRepository.save(user);
        customerRepository.save(customer);
    }

    public void updateCustomer(int userId , CustomerDTO customerDTO) {
        User user = authRepository.findUserById(userId);
        Customer customer = user.getCustomer();

        if(customer==null){
            throw new ApiException("customer not found");
        }

        if(user.getId()!=customerDTO.getUserId()){
            throw new ApiException("customer id not match");
        }

        user.setUsername(customerDTO.getUsername());
        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        customerDTO.setPassword(hash);
        user.setPassword(hash);
        user.setPassword(customerDTO.getPassword());
        user.setName(customerDTO.getName());
        user.setEmail(customerDTO.getEmail());
        user.setRole(customerDTO.getRole());
        authRepository.save(user);
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customerRepository.save(customer);
    }


    public void deleteCustomer(int userId , int id) {
        User user = authRepository.findUserById(userId);

        Customer customer = customerRepository.findCustomerById(id);

        if(customer == null){
            throw new ApiException("customer not found");
        }

        if(user.getId()!=customer.getId()) {
            throw new ApiException("customer id not match");
        }




        customer.setUser(null);
        authRepository.delete(user);
    }


}
