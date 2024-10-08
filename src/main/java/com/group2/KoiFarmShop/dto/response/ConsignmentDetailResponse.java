package com.group2.KoiFarmShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsignmentDetailResponse {

    private int consignmentID;
    private Date consignmentDate;
    private boolean consignmentType;
    private double agreedPrice;
    private String notes;
    private String email;
    private String fullname;
    private String phoneNumber;
    private int duration; // Thời gian ký gửi: 1, 3 hoặc 6 tháng
    private double serviceFee;
    private Date startDate;
    private Date endDate;
    private int status;
    private boolean online;

    private KoiFishDetailReponse koiFish;
}
