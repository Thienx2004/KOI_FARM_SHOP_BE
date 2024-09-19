package com.koifarm.koifarmshop.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderDetailID;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "koiID")
    private KoiFish koiFish;

    private boolean type; // true/false for Batch/Fish
    private int quantity;
    private double price;

    // Getters and Setters
}
