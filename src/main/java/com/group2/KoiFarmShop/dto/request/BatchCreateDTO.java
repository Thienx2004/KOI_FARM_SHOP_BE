package com.group2.KoiFarmShop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchCreateDTO {

    private String origin;
    private int quantity;
    private int age;
    private String avgSize;
    private double price;
    private int categoryID;
    private int status = 1;

}
