package com.group2.KoiFarmShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentDetailResponse {

    private int consignmentID;
    private Date consignmentDate;
    private boolean consignmentType;
    private double agreedPrice;
    private String notes;
    private String email;
    private String fullname;
    private String phoneNumber;
    private int status;
    private boolean online;

    private KoiFishReponse koiFish;
}
