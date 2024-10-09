package com.group2.KoiFarmShop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequest {
    int categoryId;
    String categoryName;
    String categoryDescription;
    String categoryImage;
    boolean status;
}
