package com.example.project3.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Employee {

    @Id
    private Integer id;

    @Column(columnDefinition = "varchar(20) not null ")
    private String position;

    @Column(columnDefinition = "double not null ")
    private double salary;

    @OneToOne
    @JsonIgnore
    private User user;
}
