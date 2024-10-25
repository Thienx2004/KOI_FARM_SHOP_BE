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

    @Query("SELECT DAY(o.order_date) as day, SUM(o.totalPrice) as totalRevenue "
            + "FROM Orders o "
            + "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE) AND MONTH(o.order_date) = :month "
            + "GROUP BY DAY(o.order_date) "
            + "ORDER BY DAY(o.order_date)")
    List<Object[]> findTotalRevenueByDay(@Param("month") int month);
    @Query("SELECT WEEK(o.order_date) as week, SUM(o.totalPrice) as totalRevenue "
            + "FROM Orders o "
            + "WHERE YEAR(o.order_date) = YEAR(CURRENT_DATE) "
            + "GROUP BY WEEK(o.order_date) "
            + "ORDER BY WEEK(o.order_date)")
    List<Object[]> findTotalRevenueByWeek();



    @Query("SELECT p.paymentID, p.amount, p.paymentDate, p.status, p.transactionCode, p.consignment.consignmentID, " +
            "p.order.orderID, p.order.order_date, p.order.status, p.order.totalPrice, p.order.account.accountID " +
            "FROM Payment p JOIN p.order o " +
            "WHERE p.transactionCode LIKE :transactionCode")
    Orders findPaymentByTransactionCode(@Param("transactionCode") String transactionCode);

    Page<Orders> findByPaymentTransactionCodeContaining(String transactionCode,Pageable pageable);


}
