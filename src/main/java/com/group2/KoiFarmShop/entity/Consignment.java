package com.group2.KoiFarmShop.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Consignment")
public class Consignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consignmentID;

    @ManyToOne
    @JoinColumn(name = "accountID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "koiID", nullable = true)
    private KoiFish koiFish;

    private boolean consignmentType; // true/false for Sale/Care
    private double agreedPrice;

    @Temporal(TemporalType.DATE)
    private Date consignmentDate;

    private String notes;
    private int status;     //1 = Pending, 2 = Confirm, 3 = Sold
    private boolean online;

}
