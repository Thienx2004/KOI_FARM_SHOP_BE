package com.group2.KoiFarmShop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReponse {

    private int id;
    private String categoryName;
    private String description;
    private String cateImg;
    private List<KoiFishReponse> koiFishList;


}
