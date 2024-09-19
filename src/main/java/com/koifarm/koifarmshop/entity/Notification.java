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
@Table(name = "Notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationID;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;

    private boolean status;

    // Getters and Setters
}
