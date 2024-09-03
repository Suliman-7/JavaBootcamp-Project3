package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Customer {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(columnDefinition = "varchar(10) not null ")
    private String phoneNumber;

    @OneToOne
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "customer")
    private Set<Account> accounts;
}
