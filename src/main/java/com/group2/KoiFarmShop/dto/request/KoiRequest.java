package com.group2.KoiFarmShop.dto.request;

import com.group2.KoiFarmShop.dto.CertificateRequest;
import lombok.Data;

@Data
public class KoiRequest {
    private String origin;
    private boolean gender;
    private int age;
    private double size;
    private String personality;
    private double price;
    private String koiImage;
    private int categoryId;
    private CertificateRequest certificate;
    private int purebred;
    private String health;
    private String temperature;
    private String water;
    private String pH;
    private String food;
}
