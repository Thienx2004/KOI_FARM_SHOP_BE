package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.BatchPageReponse;
import com.group2.KoiFarmShop.dto.reponse.BatchReponse;
import com.group2.KoiFarmShop.service.BatchServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/BatchKoi")
public class BatchController {

    @Autowired
    BatchServiceImp batchService;

    @GetMapping("")
    public ApiReponse<BatchPageReponse> getAllBatches(@RequestParam int pageNo, @RequestParam int pageSize) {
        ApiReponse apiReponse = new ApiReponse();
        BatchPageReponse batchPageReponse = batchService.getBatchList(pageNo, pageSize);
        apiReponse.setData(batchPageReponse);
        return apiReponse;
    }

    @GetMapping("/{batchId}")
    public ApiReponse<BatchReponse> getBatch(@PathVariable int batchId) {
        ApiReponse apiReponse = new ApiReponse();
        BatchReponse batchReponse = batchService.getBatchById(batchId);
        apiReponse.setData(batchReponse);
        return apiReponse;
    }

    @GetMapping("/{categoryId}/{pageNo}/{pageSize}")
    public ApiReponse<BatchPageReponse> getBatchByCategory(@PathVariable int categoryId, @PathVariable int pageNo, @PathVariable int pageSize) {
        ApiReponse apiReponse = new ApiReponse();
        BatchPageReponse batchPageReponse = batchService.getBatchByCategory(categoryId, pageNo, pageSize);
        apiReponse.setData(batchPageReponse);
        return apiReponse;
    }
}
