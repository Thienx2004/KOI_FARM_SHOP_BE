package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Consignment;
import com.group2.KoiFarmShop.entity.KoiFish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment, Integer> {
    Page<Consignment> findAll(Specification<Consignment> spec, Pageable pageable);

    Page<Consignment> findConsignmentsByAccount_AccountID(int accountId, Pageable pageable);
    Optional<Consignment> findConsignmentByConsignmentID(int consignmentID);
}
