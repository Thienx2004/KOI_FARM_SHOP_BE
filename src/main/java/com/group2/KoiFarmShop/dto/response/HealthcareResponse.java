package com.group2.KoiFarmShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class HealthcareResponse {
    private String healthStatus;
    private String growthStatus;
    private String careEnvironment;
    private String note;
    private long dayRemain;
    private boolean checked;

    public HealthcareResponse(){
        this.checked = false;
        this.dayRemain = 0;
        this.growthStatus = "";
        this.careEnvironment = "";
        this.note = "";
        this.healthStatus = "";
    }
}
