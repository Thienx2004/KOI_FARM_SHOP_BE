package com.group2.KoiFarmShop.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Consignment")
public class Consignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consignmentID;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    @OneToOne
    @JoinColumn(name = "koiID", referencedColumnName = "koiID")
    private KoiFish koiFish;

    private Boolean consignmentType; // true/false for Sale/Care
    private double agreedPrice;

    @Temporal(TemporalType.DATE)
    private Date consignmentDate;

    private String notes;
    private String status;
    private boolean online;

}
