package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.reponse.OrderHistoryReponse;
import com.group2.KoiFarmShop.dto.reponse.PaginReponse;
import com.group2.KoiFarmShop.dto.request.OrderRequest;
import com.group2.KoiFarmShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/saveOrder")
    public ApiReponse<String> saveOrder(@RequestBody OrderRequest orderRequest) {
        ApiReponse<String> resp = new ApiReponse<>();
        String success = orderService.addOrder(orderRequest);
        resp.setData(success);
        return resp;
    }

    @GetMapping("/getOrderHistory")
    public ApiReponse<PaginReponse<OrderHistoryReponse>> getAllOrdersWithDetails(
            @RequestParam(required = false) String accountID,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "3") int pageSize) {

        PaginReponse<OrderHistoryReponse> historyReponsePaginReponse = orderService.getOrdersHistory(pageNo, pageSize, accountID, type);
        ApiReponse<PaginReponse<OrderHistoryReponse>> historyReponse = new ApiReponse<>();
        historyReponse.setData(historyReponsePaginReponse);

        return historyReponse;
    }

}
