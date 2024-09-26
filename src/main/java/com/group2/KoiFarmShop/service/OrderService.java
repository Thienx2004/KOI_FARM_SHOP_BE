package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.request.OrderRequest;
import com.group2.KoiFarmShop.entity.*;
import com.group2.KoiFarmShop.exception.AppException;
import com.group2.KoiFarmShop.exception.ErrorCode;
import com.group2.KoiFarmShop.repository.BatchRepository;
import com.group2.KoiFarmShop.repository.KoiFishRepository;
import com.group2.KoiFarmShop.repository.OrderDetailRepository;
import com.group2.KoiFarmShop.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService implements OrderServiceImp{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private KoiFishRepository koiFishRepository;
    @Autowired
    private BatchRepository batchRepository;

    @Transactional
    @Override
    public String addOrder(OrderRequest order) {

        try {
            Account account = new Account();
            account.setAccountID(order.getAccountID());
            Orders orders = new Orders();
            orders.setAccount(account);
            orders.setTotalPrice(order.getTotalPrice());
            orders.setOrderDate(new Date());

            orderRepository.save(orders);

            List<OrderDetail> orderDetails = new ArrayList<>();
            if (order.getKoiFishs().length != 0) {
                for (int i = 0; i < order.getKoiFishs().length; i++) {
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
            if (order.getBatchs().length != 0) {
                for (int i = 0; i < order.getBatchs().length; i++) {
                    Batch batch = new Batch();
                    batch.setBatchID(order.getBatchs()[i]);

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrders(orders);
                    orderDetail.setBatch(batch);
                    orderDetail.setQuantity(order.getQuantity()[i]);
                    orderDetail.setType(false);
                    orderDetails.add(orderDetail);
                }
            }
            for (int i = 0; i < order.getQuantity().length; i++) {
                if(orderDetails.get(i).isType()){
                    KoiFish koiFish = koiFishRepository.findByKoiID(orderDetails.get(i).getKoiFish().getKoiID());
                    koiFish.setStatus(2);
                    koiFishRepository.save(koiFish);
                } else{
                    Batch batch = batchRepository.findByBatchID(orderDetails.get(i).getBatch().getBatchID())
                            .orElseThrow(() -> new AppException(ErrorCode.BATCH_NOT_EXISTED));
                    batch.setQuantity(order.getQuantity()[i] - batch.getQuantity());
                    batchRepository.save(batch);
                }
                orderDetails.get(i).setQuantity(order.getQuantity()[i]);
            }

            orderDetailRepository.saveAll(orderDetails);


        } catch (Exception e) {
            System.out.println("Error insert order: " + e.getMessage());
        }
        return "Order added successfully!";
    }
}
