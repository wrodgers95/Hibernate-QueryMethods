package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table (name = "customers")
public class Customer {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer (){
    }
}
