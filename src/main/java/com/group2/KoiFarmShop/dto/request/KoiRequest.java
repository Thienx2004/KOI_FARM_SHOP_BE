package com.group2.KoiFarmShop.dto.request;

import com.group2.KoiFarmShop.entity.Category;
import lombok.Data;

@Data
public class KoiRequest {
    private String origin;
    private String gender;
    private int age;
    private double size;
    private String personality;
    private double price;
    private String koiImage;
    private int categoryId;
}
