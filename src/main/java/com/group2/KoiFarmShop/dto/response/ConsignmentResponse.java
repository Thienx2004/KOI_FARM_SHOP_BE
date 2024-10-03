package com.group2.KoiFarmShop.dto.response;


import com.group2.KoiFarmShop.entity.Account;
import com.group2.KoiFarmShop.entity.KoiFish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentResponse {

    private int consignmentID;
    private boolean consignmentType; // true/false for Sale/Care
    private double agreedPrice;
    private Date consignmentDate;
    private String notes;
    private String phoneNumber;
    private String email;
    private String fullname;
    private int status;     //1 = Pending, 2 = Confirm, 3 = Sold
    private boolean online;
}
