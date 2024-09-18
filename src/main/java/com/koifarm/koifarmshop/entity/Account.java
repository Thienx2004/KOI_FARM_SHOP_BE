package com.koifarm.koifarmshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity (name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountID;

    @Column(name="Email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="fullName")
    private String fullName;

    @Column(name="phone")
    private String phone;

    @Column(name="loyaltyPoints")
    private Integer loyaltyPoints = 0;

    @ManyToOne
    @JoinColumn(name = "roleID", referencedColumnName = "roleID")
    private Role role;

    @Column(name="isVerify")
    private Boolean isVerify = false;

    @Column(name="verificationToken")
    private String verificationToken;

    @Column(name="createdAt")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="updatedAt")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name="status")
    private String status;

    @Column(name="avatar")
    private String avatar;


}
