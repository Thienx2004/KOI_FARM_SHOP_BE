package com.koifarm.koifarmshop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Entity(name = "role")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleID;

    @Column(nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private Set<Account> listAccount;
}

