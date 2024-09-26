package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.OrderRequest;
import com.group2.KoiFarmShop.entity.*;
import com.group2.KoiFarmShop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService implements OrderServiceImp{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public String addOrder(OrderRequest order) {

        Account account = new Account();
        account.setAccountID(order.getAccountID());
        Orders orders = new Orders();
        orders.setAccount(account);
        orders.setTotalPrice(order.getTotalPrice());
        orders.setOrderDate(new Date());

        List<OrderDetail> orderDetails = new ArrayList<>();
        if(order.getKoiFishs().length != 0){
            for(int i = 0; i < order.getKoiFishs().length; i++){
                KoiFish koiFish = new KoiFish();
                koiFish.setKoiID(order.getKoiFishs()[i]);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrders(orders);
                orderDetail.setKoiFish(koiFish);
                orderDetail.setType(true);
                orderDetail.setQuantity(order.getQuantity()[i]);
                orderDetails.add(orderDetail);
            }
        }
        if(order.getBatchs().length != 0){
            for(int i = 0; i < order.getBatchs().length; i++){
                Batch batch = new Batch();
                batch.setBatchID(order.getBatchs()[i]);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrders(orders);
                orderDetail.setBatch(batch);
                orderDetail.setQuantity(order.getQuantity()[i]);
                orderDetail.setType(false);
            }
        }

        return "";
    }
}
