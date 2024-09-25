package com.group2.KoiFarmShop.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KoiFishReponse {
    private String origin;
    private String gender;
    private int age;
    private double size;
    private String personality;
    private double price;
    private String koiImage;
}
