package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.BatchPageReponse;
import com.group2.KoiFarmShop.dto.reponse.BatchReponse;
import com.group2.KoiFarmShop.entity.Batch;
import com.group2.KoiFarmShop.entity.Category;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService implements BatchServiceImp{

    @Autowired
    private BatchRepository batchRepository;

    @Override
    public BatchPageReponse getBatchList(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Batch> batchPage = batchRepository.findAll(pageable);
        List<Batch> batchList = batchPage.getContent();
        List<BatchReponse> batchReponseList = new ArrayList<>();
        BatchPageReponse batchPageReponse = new BatchPageReponse();
        for(Batch batch : batchList) {
            BatchReponse batchReponse = new BatchReponse();
            batchReponse.setBatchID(batch.getBatchID());
            batchReponse.setOrigin(batch.getOrigin());
            batchReponse.setQuantity(batch.getQuantity());
            batchReponse.setPrice(batch.getPrice());
            batchReponse.setStatus(batch.getStatus());

            batchReponseList.add(batchReponse);
        }

        batchPageReponse.setBatchReponses(batchReponseList);
        batchPageReponse.setPageNum(batchPage.getNumber());
        batchPageReponse.setPageSize(batchPage.getSize());
        batchPageReponse.setTotalElements(batchPage.getNumberOfElements());
        batchPageReponse.setTotalPages(batchPage.getTotalPages());


        return batchPageReponse;
    }


    @Override
    public BatchReponse getBatchById(int id) {
        Batch batch = batchRepository.findByBatchID(id)
                .orElseThrow(() -> new AppException(ErrorCode.BATCH_NOT_EXISTED));
        BatchReponse batchReponse = new BatchReponse();
        batchReponse.setBatchID(batch.getBatchID());
        batchReponse.setOrigin(batch.getOrigin());
        batchReponse.setQuantity(batch.getQuantity());
        batchReponse.setPrice(batch.getPrice());
        batchReponse.setStatus(batch.getStatus());

        return batchReponse;
    }

    @Override
    public String addBatch(Batch batch) {
        return "";
    }

    @Override
    public String updateBatch(Batch batch) {
        return "";
    }

    @Override
    public String deleteBatch(int id) {
        return "";
    }

    @Override
    public BatchPageReponse getBatchByCategory(int categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Category category = new Category();
        category.setCategoryID(categoryId);
        Page<Batch> batchPage = batchRepository.findByCategory(category, pageable);
        List<Batch> batchList = batchPage.getContent();
        List<BatchReponse> batchReponseList = new ArrayList<>();
        BatchPageReponse batchPageReponse = new BatchPageReponse();
        for(Batch batch : batchList) {
            BatchReponse batchReponse = new BatchReponse();
            batchReponse.setBatchID(batch.getBatchID());
            batchReponse.setOrigin(batch.getOrigin());
            batchReponse.setQuantity(batch.getQuantity());
            batchReponse.setPrice(batch.getPrice());
            batchReponse.setStatus(batch.getStatus());

            batchReponseList.add(batchReponse);
        }

        batchPageReponse.setBatchReponses(batchReponseList);
        batchPageReponse.setPageNum(batchPage.getNumber());
        batchPageReponse.setPageSize(batchPage.getSize());
        batchPageReponse.setTotalElements(batchPage.getNumberOfElements());
        batchPageReponse.setTotalPages(batchPage.getTotalPages());


        return batchPageReponse;
    }

}
