package com.group2.KoiFarmShop.dto.request;

import com.google.firebase.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCategoryRequest {

    String categoryName;
    String categoryDescription;
//    String categoryImage;
    boolean status;
    @Nullable
    MultipartFile imgFile;
}
