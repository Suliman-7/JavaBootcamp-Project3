package com.example.project3.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "user name should be not empty")
    @Column(columnDefinition = "varchar(10) not null unique")
    @Size(min = 4 , max = 10 , message = "user name length should be at least 4 characters and at most 10 characters")
    private String username;

    @NotEmpty(message = "password should be not empty")
    @Size(min = 6 ,  message = "password length must be 6 characters")
    @Column(columnDefinition = "varchar(100) not null")
    private String password;

    @NotEmpty(message = "name should be not empty")
    @Column(columnDefinition = "varchar(10) not null ")
    @Size(min = 2 , max = 20 , message = "name length should be at least 2 characters and at most 20 characters")
    private String name;

    @Email
    @NotEmpty(message = "email should be not empty")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String email;

    @NotEmpty(message = "role should be not empty")
    @Pattern(regexp = "^(CUSTOMER|ADMIN|EMPLOYEE)$")
    private String role;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL , mappedBy = "user")
    private Employee employee;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }






}
