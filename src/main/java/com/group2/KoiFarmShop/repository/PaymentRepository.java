package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Certificate;
import com.group2.KoiFarmShop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
