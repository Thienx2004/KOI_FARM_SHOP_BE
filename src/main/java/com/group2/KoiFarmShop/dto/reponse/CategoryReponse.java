package com.group2.KoiFarmShop.dto.reponse;

import com.group2.KoiFarmShop.entity.Batch;
import com.group2.KoiFarmShop.entity.KoiFish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryReponse {

    private String categoryName;
    private String description;
    private String cateImg;
    private List<KoiFishReponse> koiFishList;


}
