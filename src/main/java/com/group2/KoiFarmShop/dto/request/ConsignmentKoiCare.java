package com.group2.KoiFarmShop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsignmentKoiCare {
    private int koiCareId;
    private String healthStatus;
    private Double growthStatus;
    private String careEnvironment;
    private String note;
}
