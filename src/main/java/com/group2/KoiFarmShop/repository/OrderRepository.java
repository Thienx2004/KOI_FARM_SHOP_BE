package com.group2.KoiFarmShop.repository;

import com.group2.KoiFarmShop.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("SELECT o FROM Orders o " +
            "JOIN o.payment p " +
            "JOIN FETCH o.orderDetails od " +
            "LEFT JOIN od.koiFish k " +
            "LEFT JOIN od.batch b " +
            "WHERE (:accountID IS NULL OR o.account.accountID = :accountID) " +
            //"AND (:type IS NULL OR od.type = :type) " +
            "ORDER BY o.order_date DESC")
    Page<Orders> findOrdersWithFilters(@Param("accountID") Integer accountID,
                                       //@Param("type") Boolean type,
                                       Pageable pageable);

    // Tổng doanh thu 7 năm gần nhất theo năm
    @Query("SELECT YEAR(o.order_date) as year, SUM(o.totalPrice) as totalRevenue "
            + "FROM Orders o "
            + "WHERE o.order_date >= :startDate "
            + "GROUP BY YEAR(o.order_date)")
    List<Object[]> findTotalRevenueByYear(@Param("startDate") Date startDate);

    // Tổng doanh thu của các tháng trong năm hiện tại
    @Query("SELECT MONTH(o.order_date) as month, SUM(o.totalPrice) as totalRevenue "
            + "FROM Orders o "
            + "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE) "
            + "GROUP BY MONTH(o.order_date) "
            + "ORDER BY MONTH(o.order_date)")
    List<Object[]> findTotalRevenueByMonth();

    // Tổng số đơn hàng trong tháng hiện tại
    @Query("SELECT COUNT(o) FROM Orders o "
            + "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE) "
            + "AND MONTH(o.order_date) = MONTH(CURRENT_DATE)")
    int countOrdersInCurrentMonth();
}
