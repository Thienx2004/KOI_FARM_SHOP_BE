package com.group2.KoiFarmShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
public class HealthcareResponse {
    private String healthStatus;
    private Double growthStatus;
    private String careEnvironment;
    private String note;
    private Date date;
    private long dayRemain;
    private boolean checked;

    public HealthcareResponse(){
        this.checked = false;
        this.dayRemain = 0;
        this.growthStatus = 0.0;
        this.careEnvironment = "";
        this.note = "";
        this.healthStatus = "";
    }
}
