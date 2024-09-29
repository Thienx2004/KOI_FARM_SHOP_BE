package com.group2.KoiFarmShop.dto.reponse;

import com.group2.KoiFarmShop.dto.CertificateRequest;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.Certificate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KoiFishReponse {
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
    private CertificateRequest certificate;

  
    private int status;

}
