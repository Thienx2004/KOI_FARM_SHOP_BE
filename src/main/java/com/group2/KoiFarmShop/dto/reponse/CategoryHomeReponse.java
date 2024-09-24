package com.group2.KoiFarmShop.dto.reponse;

import com.group2.KoiFarmShop.entity.KoiFish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHomeReponse {
    List<CategoryReponse> categoryReponses;
    private int totalElements;
    private int totalPages;
}
