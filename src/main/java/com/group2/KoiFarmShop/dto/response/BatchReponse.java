package com.group2.KoiFarmShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchReponse {

    private int batchID;
    private int quantity;
    private String origin;
    private int age;
    private String avgSize;
    private double price;
    private int categoryID;
    private String categoryName;
    private String batchImg;

    private int status;

}
