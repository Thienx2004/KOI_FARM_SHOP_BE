package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Consignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment, Integer> {



}
