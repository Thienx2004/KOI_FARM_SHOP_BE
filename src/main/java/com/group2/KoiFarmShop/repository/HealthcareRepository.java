package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Certificate;
import com.group2.KoiFarmShop.entity.Healthcare;
import com.group2.KoiFarmShop.entity.KoiFish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface HealthcareRepository extends JpaRepository<Healthcare, Integer> {
    Optional<Healthcare> findHealthcareByKoiFish(KoiFish koiFish);
    @Query("SELECT  h FROM Healthcare h WHERE h.koiFish = :koiFish ORDER BY h.createdDate DESC LIMIT 1")
    Optional<Healthcare> findLatestHealthcareByKoiFish(@Param("koiFish") KoiFish koiFish);

