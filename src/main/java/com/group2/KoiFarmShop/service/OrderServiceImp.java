package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.response.OrderDetailReponse;
import com.group2.KoiFarmShop.dto.response.OrderHistoryReponse;
import com.group2.KoiFarmShop.dto.response.PaginReponse;
import com.group2.KoiFarmShop.dto.request.OrderRequest;
import com.group2.KoiFarmShop.entity.Orders;
import jakarta.mail.MessagingException;

import java.util.List;

public interface OrderServiceImp {

    public Orders addOrder(OrderRequest order) throws MessagingException;
    public PaginReponse<OrderHistoryReponse> getOrdersHistory(int pageNo, int pageSize, String accountId);

    //PaginReponse<OrderHistoryReponse> getOrdersHistory(int pageNo, int pageSize, String accountId, String type);
    public List<OrderDetailReponse> getOrderDetail(int orderId);

}
