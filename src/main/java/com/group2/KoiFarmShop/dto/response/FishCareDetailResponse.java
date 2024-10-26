package com.group2.KoiFarmShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FishCareDetailResponse {
    private int id;
    private String origin;
    private boolean gender;
    private int age;
    private double size;
    private String personality;
    private double price;
    private String koiImage;
    private int categoryId;
    private String category;
    private CertificateResponse certificate;
    private int purebred;
    private String health;
    private String temperature;
    private String water;
    private String pH;
    private String food;
    private int status;
    private List<HealthcareResponse> healthcare;
}
