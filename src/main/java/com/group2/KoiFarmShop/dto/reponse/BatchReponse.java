package com.group2.KoiFarmShop.dto.reponse;

import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.entity.OrderDetail;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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

    private int status;

}
