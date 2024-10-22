package com.group2.KoiFarmShop.dto;

import com.group2.KoiFarmShop.entity.Batch;
import com.group2.KoiFarmShop.entity.KoiFish;
import org.springframework.data.jpa.domain.Specification;

public class BatchSpec {
    public static Specification<Batch> hasKeyword(String keyword) {
        return (root, query, builder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return builder.conjunction();
            }
            String likePattern = "%" + keyword.toLowerCase() + "%";
            return builder.or(
//                 builder.like(builder.lower(root.get("origin")), likePattern),
//                builder.like(builder.lower(root.get("personality")), likePattern),
//                builder.like(builder.lower(root.get("health")), likePattern),
//                builder.like(builder.lower(root.get("temperature")), likePattern),
//                builder.like(builder.lower(root.get("water")), likePattern),
//                builder.like(builder.lower(root.get("pH")), likePattern),
//                builder.like(builder.lower(root.get("food")), likePattern),
//                builder.like(builder.lower(root.get("koiImage")), likePattern),
                    builder.like(root.get("age").as(String.class), likePattern),
                    builder.lessThanOrEqualTo(root.get("quantity").as(String.class), likePattern),    // Chuyển age thành chuỗi để tìm kiếm
                    // Chuyển age thành chuỗi để tìm kiếm
//                    builder.like(root.get("gender").as(String.class), likePattern),
                    builder.like(root.get("avgSize"), likePattern),
                    builder.like(root.get("category").get("categoryName").as(String.class), likePattern)

            );
        };
    }
}
