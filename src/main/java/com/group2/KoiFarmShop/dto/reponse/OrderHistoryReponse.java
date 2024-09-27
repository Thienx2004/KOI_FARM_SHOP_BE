package com.group2.KoiFarmShop.dto.reponse;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryReponse {

    private int orderId;
    private int accountId;
    private Date createdDate;
    private double totalPrice;
    private List<OrderDetailReponse> orderDetails;
}
