package com.group2.KoiFarmShop.controller;

import com.group2.KoiFarmShop.dto.response.ApiReponse;
import com.group2.KoiFarmShop.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/revenue/years")
    public ApiReponse<List<Object[]>> getTotalRevenueByYear() {
        ApiReponse<List<Object[]>> response = new ApiReponse<>();
        List<Object[]> list = dashboardService.getTotalRevenueByYear();
        response.setData(list);
        return response;
    }

    @GetMapping("/revenue/months")
    public ApiReponse<List<Object[]>> getTotalRevenueByMonth() {
        ApiReponse<List<Object[]>> response = new ApiReponse<>();
        List<Object[]> list = dashboardService.getTotalRevenueByMonth();
        response.setData(list);
        return response;
    }

    @GetMapping("/total-accounts")
    public ApiReponse<Integer> totalAccounts() {
        ApiReponse<Integer> response = new ApiReponse<>();
        response.setData(dashboardService.getTotalAccount());
        return response;
    }

    @GetMapping("/total-orders")
    public ApiReponse<Integer> totalOrders() {

        ApiReponse<Integer> response = new ApiReponse<>();
        response.setData(dashboardService.getTotalOrdersCurrentMonth());
        return response;
    }

    @GetMapping("/total-koi")
    public ApiReponse<Integer> totalKoi() {
        ApiReponse<Integer> response = new ApiReponse<>();
        response.setData(dashboardService.getTotalKoiFish());
        return response;
    }

    @GetMapping("/total-batch")
    public ApiReponse<Integer> totalBatch() {
        ApiReponse<Integer> response = new ApiReponse<>();
        response.setData(dashboardService.getTotalBatch());
        return response;
    }
}
