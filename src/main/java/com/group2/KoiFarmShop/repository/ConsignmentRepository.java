package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.dto.response.KoiFishPageResponse;
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
    Optional<Consignment> findConsignmentByKoiFish_KoiID(int id);
    Page<Consignment> findConsignmentsByAccount_AccountID(int accountId, Pageable pageable);
    Optional<Consignment> findConsignmentByConsignmentID(int consignmentID);

    @Query("SELECT k FROM KoiFish k " +
            "JOIN k.consignment c " +
            "WHERE c.account.accountID = :accountId AND k.status = 5")
    Page<KoiFish> findKoiFishByAccountIdAndStatus(int accountId,Pageable pageable);
    @Query("SELECT k FROM KoiFish k " +
            "JOIN k.consignment c " +
            "WHERE c.account.accountID = :accountId AND k.status = 3")
    Page<KoiFish> findKoiFishByAccountIdAndStatusSell(int accountId,Pageable pageable);
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

    @Query("SELECT DAY(c.consignmentDate) as day, SUM(c.serviceFee) as totalRevenue "
            + "FROM Consignment c "
            + "WHERE YEAR(c.consignmentDate) = YEAR(CURRENT_DATE) AND MONTH(c.consignmentDate) = :month "
            + "GROUP BY DAY(c.consignmentDate) "
            + "ORDER BY DAY(c.consignmentDate)")
    List<Object[]> findConsignmentRevenueByDay(@Param("month") int month);

    @Query("SELECT WEEK(c.consignmentDate) as week, SUM(c.serviceFee) as totalRevenue "
            + "FROM Consignment c "
            + "WHERE YEAR(c.consignmentDate) = YEAR(CURRENT_DATE) "
            + "GROUP BY WEEK(c.consignmentDate) "
            + "ORDER BY WEEK(c.consignmentDate)")
    List<Object[]> findConsignmentRevenueByWeek();

    Page<Consignment> findConsignmentByAccount_AccountIDAndStatusAndConsignmentType(int accountId, int status, boolean consignmentType, Pageable pageable);
}
