package com.jsp.swiftmart.order_service.service.impl;

import com.jsp.swiftmart.order_service.client.CartClient;
import com.jsp.swiftmart.order_service.client.InventoryClient;
import com.jsp.swiftmart.order_service.client.ProductClient;
import com.jsp.swiftmart.order_service.client.dto.CartResponse;
import com.jsp.swiftmart.order_service.dao.OrderRepository;
import com.jsp.swiftmart.order_service.dto.OrderResponse;
import com.jsp.swiftmart.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    @Override
    public OrderResponse createOrder(Long userId) {
        //find cart by user Id
        CartResponse response=cartClient.findCartByUserId(userId);
        CartClient cartClient1=null;
        //find product by product id
        //find inventory
        //payment service later
        return null;
    }

    @Override
    public OrderResponse findOrderByOrderId(Long userId, Long orderId) {
        return null;
    }

    @Override
    public Pageable findAllOrder(Long userId, int pageNo, int size) {
        return null;
    }
}
