package com.group2.KoiFarmShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchID;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;


    private int quantity;
    private String origin;
    private double price;

    @OneToMany(mappedBy = "batch")
    private Set<Feedback> feedbacks;

    private int status = 1;

    // Getters and Setters
}
