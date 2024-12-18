package com.group2.KoiFarmShop.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailReponse {

    private int orderDetailId;
    private String categoryName;
    private int koiFishId;
    private int koiAge;
    private boolean gender;
    private double koiSize;
    private String koiImg;
    private CertificationReponse certification;
    private int batchId;
    private String avgSize;
    private int batchAge;
    private double price;
    private String batchImg;
    private int quantity;
    private boolean type;
}
