package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.BatchPageReponse;
import com.group2.KoiFarmShop.dto.reponse.BatchReponse;
import com.group2.KoiFarmShop.entity.Batch;

import java.util.List;

public interface BatchServiceImp {

    public BatchPageReponse getBatchList(int pageNo, int pageSize);
    public BatchReponse getBatchById(int id);
    public String addBatch(Batch batch);
    public String updateBatch(Batch batch);
    public String deleteBatch(int id);
    public BatchPageReponse getBatchByCategory(int categoryId, int pageNo, int pageSize);

}
