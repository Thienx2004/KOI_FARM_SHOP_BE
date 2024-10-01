package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.OrderDetailReponse;
import com.group2.KoiFarmShop.dto.reponse.OrderHistoryReponse;
import com.group2.KoiFarmShop.dto.reponse.PaginReponse;
import com.group2.KoiFarmShop.dto.request.OrderRequest;
import com.group2.KoiFarmShop.entity.Orders;

import java.util.List;

public interface OrderServiceImp {

    public Orders addOrder(OrderRequest order);
    public PaginReponse<OrderHistoryReponse> getOrdersHistory(int pageNo, int pageSize, String accountId);

    //PaginReponse<OrderHistoryReponse> getOrdersHistory(int pageNo, int pageSize, String accountId, String type);
    public List<OrderDetailReponse> getOrderDetail(int orderId);

}
