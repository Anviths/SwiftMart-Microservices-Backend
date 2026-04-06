package com.jsp.swiftmart.order_service.service;

import com.jsp.swiftmart.order_service.dto.OrderRequest;
import com.jsp.swiftmart.order_service.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse
}
