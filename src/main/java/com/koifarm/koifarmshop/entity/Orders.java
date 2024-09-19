package com.koifarm.koifarmshop.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    private double totalPrice;
    private boolean status;

    // Getters and Setters
}
