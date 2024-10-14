package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Batch;
import com.group2.KoiFarmShop.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {

    public Optional<Batch> findByBatchID(int batchId);

    Page<Batch> findByCategory(Category category, Pageable pageable);
    Page<Batch> findAll(Specification<Batch> spec, Pageable pageable);

    // Tổng số Batch có status là 1
    @Query("SELECT COUNT(b) FROM Batch b WHERE b.status = 1")
    int findTotalBatchWithStatus1();
}
