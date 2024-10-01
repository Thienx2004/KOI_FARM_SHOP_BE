package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.OrderDetailReponse;
import com.group2.KoiFarmShop.dto.reponse.OrderHistoryReponse;
import com.group2.KoiFarmShop.dto.reponse.PaginReponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            orders.setOrder_date(new Date());

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
                    batch.setQuantity(batch.getQuantity() - order.getQuantity()[i]);
                    if(batch.getQuantity() - order.getQuantity()[i] == 0){
                        batch.setStatus(2);
                    }
                    batchRepository.save(batch);
                }
                orderDetails.get(i).setQuantity(order.getQuantity()[i]);
            }

            orderDetailRepository.saveAll(orderDetails);


        } catch (Exception e) {
            System.out.println("Error insert order: " + e.getMessage());
        }
        return "Thanh toán thành công!";
    }

    @Override
    public PaginReponse<OrderHistoryReponse> getOrdersHistory(int pageNo, int pageSize, String accountId, String type) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("order_date").descending());
        // Kiểm tra null hoặc chuỗi rỗng trước khi chuyển đổi
        Integer accountIdInt = (accountId != null && !accountId.isEmpty()) ? Integer.parseInt(accountId) : null;
        Boolean typeBool = (type != null && !type.isEmpty()) ? Boolean.parseBoolean(type) : null;

        // Gọi đến repository với các tham số đã kiểm tra
        Page<Orders> orders = orderRepository.findOrdersWithFilters(accountIdInt, typeBool, pageable);
        List<OrderHistoryReponse> orderHistoryReponses = new ArrayList<>();

        for (Orders order : orders.getContent()) {
            OrderHistoryReponse orderHistoryReponse = new OrderHistoryReponse();
            orderHistoryReponse.setOrderId(order.getOrderID());
            orderHistoryReponse.setAccountId(order.getAccount().getAccountID());
            orderHistoryReponse.setCreatedDate(order.getOrder_date());
            orderHistoryReponse.setTotalPrice(order.getTotalPrice());

            List<OrderDetailReponse> orderDetails = new ArrayList<>();
            for(OrderDetail orderDetail : order.getOrderDetails()){
                OrderDetailReponse orderDetailReponse = new OrderDetailReponse();

                orderDetailReponse.setOrderDetailId(orderDetail.getOrderDetailID());
                if(orderDetail.getKoiFish() != null) {
                    orderDetailReponse.setCategoryName(orderDetail.getKoiFish().getCategory().getCategoryName());
                    orderDetailReponse.setKoiFishId(orderDetail.getKoiFish().getKoiID());
                    orderDetailReponse.setGender(orderDetail.getKoiFish().isGender());
                    orderDetailReponse.setKoiAge(orderDetail.getKoiFish().getAge());
                    orderDetailReponse.setKoiSize(orderDetail.getKoiFish().getSize());
                } else {
                    orderDetailReponse.setCategoryName(orderDetail.getBatch().getCategory().getCategoryName());
                    orderDetailReponse.setBatchId(orderDetail.getBatch().getBatchID());
                    orderDetailReponse.setAvgSize(orderDetail.getBatch().getAvgSize());
                    orderDetailReponse.setKoiPrice(orderDetail.getBatch().getPrice());
                    orderDetailReponse.setBatchPrice(orderDetail.getBatch().getPrice());
                }
                orderDetailReponse.setQuantity(orderDetail.getQuantity());
                orderDetailReponse.setType(orderDetail.isType());
                orderDetailReponse.setQuantity(orderDetail.getQuantity());

                orderDetails.add(orderDetailReponse);
            }
            orderHistoryReponse.setOrderDetails(orderDetails);
            orderHistoryReponses.add(orderHistoryReponse);
        }

        PaginReponse<OrderHistoryReponse> paginReponse = new PaginReponse<>();
        paginReponse.setContent(orderHistoryReponses);
        paginReponse.setPageNum(pageNo);
        paginReponse.setPageSize(pageSize);
        paginReponse.setTotalElements(orders.getNumberOfElements());
        paginReponse.setTotalPages(orders.getTotalPages());

        return paginReponse;
    }


}
