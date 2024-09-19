package com.koifarm.koifarmshop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int feedbackID;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "batchID")
    private Batch batch;

    private int rating;
    private String comment;


}