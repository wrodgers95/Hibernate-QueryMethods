package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "purchases")
public class Purchase {

    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String date;
    @Column(nullable = false)
    String creditCard;
    @Column(nullable = false)
    int cvv;
    @Column(nullable = false)
    String category;

    @ManyToOne
    Customer customer;

    public Purchase(Customer customer, String date, String creditCard, int cvv, String category) {
        this.customer = customer;
        this.date = date;
        this.creditCard = creditCard;
        this.cvv = cvv;
        this.category = category;
    }

    public Purchase() {
    }
}
