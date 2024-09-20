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
    @Table(name = "Account")
    public class Account {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int accountID;

        @Column(nullable = false, unique = true)
        private String email;

        @Column(nullable = false)
        private String password;

        private String fullName;
        private int loyaltyPoints = 0;
        private String verificationToken;
        private boolean isVerified = false;
        private String address;
        private String phone;

        @ManyToOne
        @JoinColumn(name = "roleID")
        private Role role;

        @OneToMany(mappedBy = "account")
        private Set<Orders> orders;

        @OneToMany(mappedBy = "account")
        private Set<Feedback> feedbacks;

        @OneToOne(mappedBy = "account")
        private ForgotPassword forgotPassword;

        private boolean status = true;
}
