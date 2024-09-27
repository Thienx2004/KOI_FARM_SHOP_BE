package com.group2.KoiFarmShop.dto.reponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginReponse<T> {

    private List<T> content;
    private int pageNum;
    private int pageSize;
    private int totalElements;
    private int totalPages;
}
