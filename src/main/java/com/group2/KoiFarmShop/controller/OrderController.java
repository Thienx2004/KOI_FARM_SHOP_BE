package com.group2.KoiFarmShop.controller;


import com.group2.KoiFarmShop.dto.reponse.ApiReponse;
import com.group2.KoiFarmShop.dto.request.OrderRequest;
import com.group2.KoiFarmShop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
