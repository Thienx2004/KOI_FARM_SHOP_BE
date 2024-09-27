package com.group2.KoiFarmShop.dto.reponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailReponse {

    private int orderDetailId;
    private String categoryName;
    private int koiFishId;
    private int koiAge;
    private String gender;
    private double koiSize;
    private int batchId;
    private String avgSize;
    private double koiPrice;
    private double batchPrice;
    private int quantity;
    private boolean type;
}
