package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Consignment;
import com.group2.KoiFarmShop.entity.KoiFish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsignmentRepository extends JpaRepository<Consignment, Integer> {
    Page<Consignment> findAll(Specification<Consignment> spec, Pageable pageable);

    Page<Consignment> findConsignmentsByAccount_AccountID(int accountId, Pageable pageable);
    Optional<Consignment> findConsignmentByConsignmentID(int consignmentID);

    // Tổng doanh thu 7 năm gần nhất theo năm
    @Query("SELECT YEAR(c.consignmentDate) as year, SUM(c.serviceFee) as totalRevenue "
            + "FROM Consignment c "
            + "WHERE c.consignmentDate >= :startDate "
            + "AND (c.status = 2 OR c.status = 5) "
            + "GROUP BY YEAR(c.consignmentDate)")
    List<Object[]> findTotalRevenueByYear(@Param("startDate") Date startDate);

    // Tổng doanh thu của các tháng trong năm hiện tại
    @Query("SELECT MONTH(c.consignmentDate) as month, SUM(c.serviceFee) as totalRevenue "
            + "FROM Consignment c "
            + "WHERE YEAR(c.consignmentDate) = YEAR(CURRENT_DATE) "
            + "AND (c.status = 2 OR c.status = 5) "
            + "GROUP BY MONTH(c.consignmentDate) "
            + "ORDER BY MONTH(c.consignmentDate)")
    List<Object[]> findTotalRevenueByMonth();
}
