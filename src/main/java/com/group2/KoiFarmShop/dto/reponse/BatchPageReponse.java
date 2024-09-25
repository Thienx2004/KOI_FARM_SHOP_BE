package com.group2.KoiFarmShop.dto.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchPageReponse {

    List<BatchReponse> batchReponses;
    private int pageNum;
    private int pageSize;
    private int totalElements;
    private int totalPages;

}
