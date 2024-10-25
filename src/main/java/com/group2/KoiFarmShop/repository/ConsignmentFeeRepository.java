package com.group2.KoiFarmShop.repository;


import com.group2.KoiFarmShop.entity.ConsignmentFee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsignmentFeeRepository extends JpaRepository<ConsignmentFee, Integer> {
    ConsignmentFee findConsignmentFeeByConsignmentFeeId(int consignmentId);
}
