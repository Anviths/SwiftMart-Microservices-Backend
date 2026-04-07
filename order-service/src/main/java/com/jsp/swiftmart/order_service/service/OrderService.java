package com.jsp.swiftmart.order_service.service;

import com.jsp.swiftmart.order_service.dto.OrderRequest;
import com.jsp.swiftmart.order_service.dto.OrderResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponse createOrder(Long useId);
    OrderResponse findOrderByOrderId(Long userId,Long orderId);
    Pageable findAllOrder(Long userId,int pageNo,int size);
}
