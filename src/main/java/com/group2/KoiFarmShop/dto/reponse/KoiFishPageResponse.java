package com.group2.KoiFarmShop.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KoiFishPageResponse {
    private int pageNum;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    List<KoiFishReponse> koiFishReponseList;
}
