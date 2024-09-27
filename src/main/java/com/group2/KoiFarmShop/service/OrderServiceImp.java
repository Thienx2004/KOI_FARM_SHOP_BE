package com.group2.KoiFarmShop.service;

import com.group2.KoiFarmShop.dto.reponse.OrderHistoryReponse;
import com.group2.KoiFarmShop.dto.reponse.PaginReponse;
import com.group2.KoiFarmShop.dto.request.OrderRequest;

public interface OrderServiceImp {

    public String addOrder(OrderRequest order);
    public PaginReponse<OrderHistoryReponse> getOrdersHistory(int pageNo, int pageSize, String accountId, String type);
}
