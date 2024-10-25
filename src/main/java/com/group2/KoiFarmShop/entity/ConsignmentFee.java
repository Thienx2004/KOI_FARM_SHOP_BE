package com.group2.KoiFarmShop.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignmentFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int consignmentFeeId;
    private int duration;
    private double rate;
    private Date createdDate;
    private boolean sale;       //sale = true , false = care
    private boolean status;

}
