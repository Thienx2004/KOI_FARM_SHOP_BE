package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.repository.AccountRepository;
import com.group2.KoiFarmShop.repository.BatchRepository;
import com.group2.KoiFarmShop.repository.KoiFishRepository;
import com.group2.KoiFarmShop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private KoiFishRepository koiFishRepository;

    @Autowired
    private BatchRepository batchRepository;

    public List<Object[]> getTotalRevenueByYear(){
        // Lấy ngày bắt đầu từ 7 năm trước
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -7);
        Date startDate = calendar.getTime();

        // Lấy kết quả doanh thu từ database
        List<Object[]> revenueList = orderRepository.findTotalRevenueByYear(startDate);

        // Tạo một map để lưu doanh thu theo năm
        Map<Integer, Double> revenueMap = new HashMap<>();
        for (Object[] obj : revenueList) {
            Integer year = (Integer) obj[0];
            Double totalRevenue = (Double) obj[1];
            revenueMap.put(year, totalRevenue);
        }

        // Tạo danh sách các năm trong 7 năm gần nhất
        List<Object[]> result = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear - 7; year <= currentYear; year++) {
            Double totalRevenue = revenueMap.getOrDefault(year, 0.0);
            result.add(new Object[]{year, totalRevenue});
        }

        return result;
    }

    public List<Object[]> getTotalRevenueByMonth(){

        // Lấy kết quả doanh thu từ database
        List<Object[]> revenueList = orderRepository.findTotalRevenueByMonth();

        // Tạo một map để lưu doanh thu theo tháng
        Map<Integer, Double> revenueMap = new HashMap<>();
        for (Object[] obj : revenueList) {
            Integer month = (Integer) obj[0];
            Double totalRevenue = (Double) obj[1];
            revenueMap.put(month, totalRevenue);
        }

        // Tạo danh sách 12 tháng trong năm
        List<Object[]> result = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            Double totalRevenue = revenueMap.getOrDefault(month, 0.0);
            result.add(new Object[]{month, totalRevenue});
        }

        return result;
    }

    public int getTotalAccount(){
        return accountRepository.findTotalAccounts();
    }

    public int getTotalOrdersCurrentMonth(){
        return orderRepository.countOrdersInCurrentMonth();
    }

    public int getTotalKoiFish(){
        return koiFishRepository.findTotalKoiWithStatus1Or3();
    }

    public int getTotalBatch(){
        return batchRepository.findTotalBatchWithStatus1();
    }

}