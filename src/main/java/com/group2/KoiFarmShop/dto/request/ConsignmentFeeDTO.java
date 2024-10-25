package com.group2.KoiFarmShop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsignmentFeeDTO {

    private int consignmentFeeId;
    private int duration;
    private double rate;
    private Date createdDate;
    private boolean sale;
    private boolean status;
}
