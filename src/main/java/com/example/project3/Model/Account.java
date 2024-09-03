package com.example.project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "account number should be not empty")
    @Pattern(regexp = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")
    @Column(columnDefinition = "varchar(20) not null unique")
    private String accountNumber;

    @NotNull(message = "balance should be not empty")
    @Column(columnDefinition = "double not null ")
    private double balance;

    // @AssertFalse(message = "Account status should be initially false")
    private boolean isActive;

    @ManyToOne
    @JsonIgnore
    private Customer customer;



}
