package com.group2.KoiFarmShop.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class ConsignmentRequest {
    private int accountid;
    private boolean consignmentType; // true/false for Sale/Care
    private double agreedPrice;
    private Date consignmentDate;
    private String notes;
    private String phoneNumber;
    private String email;
    private String fullname;
    private int duration; // Thời gian ký gửi: 1, 3 hoặc 6 tháng
    private double serviceFee;
    private Date startDate;
    private Date endDate;
    private int status;     //1 = Pending, 2 = Confirm, 3 = Sold
    private boolean online;
}