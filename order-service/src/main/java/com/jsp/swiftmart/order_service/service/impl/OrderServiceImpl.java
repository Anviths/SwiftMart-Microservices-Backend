package com.jsp.swiftmart.order_service.service.impl;

import com.jsp.swiftmart.order_service.client.CartClient;
import com.jsp.swiftmart.order_service.client.InventoryClient;
import com.jsp.swiftmart.order_service.client.dto.CartResponse;
import com.jsp.swiftmart.order_service.dao.OrderRepository;
import com.jsp.swiftmart.order_service.dto.InventoryRequest;
import com.jsp.swiftmart.order_service.dto.OrderResponse;
import com.jsp.swiftmart.order_service.dto.StockCheckResponse;
import com.jsp.swiftmart.order_service.entity.Order;
import com.jsp.swiftmart.order_service.entity.OrderItem;
import com.jsp.swiftmart.order_service.entity.OrderStatus;
import com.jsp.swiftmart.order_service.exception.CartException;
import com.jsp.swiftmart.order_service.exception.OrderException;
import com.jsp.swiftmart.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
//product id 31 ,32
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final CheckStockWithResilience inventoryClient;
    @Override
    @Transactional
    public OrderResponse createOrder(Long userId,Long warehouseId) {
        //find cart by user Id
        CartResponse cart=cartClient.findCartByUserId(userId);

        if (!cart.isActive()) {
            throw new CartException("Cart already ordered");
        }
        if (cart.getItems().isEmpty()){
            throw new CartException("cart is empty");
        }
        List<InventoryRequest> requests=cart.getItems().stream()
                .map(item->new InventoryRequest(
                        item.getProductId(), item.getQuantity()
                )).toList();



        List<StockCheckResponse> stockResponses = inventoryClient.checkStockWithResilience(requests,warehouseId);

        List<StockCheckResponse> failedItems = stockResponses.stream()
                .filter(res -> !res.isInStock())
                .toList();

        if (!failedItems.isEmpty()) {

            String errorMessage = failedItems.stream()
                    .map(item -> "Product " + item.getProductId() + ": " + item.getMessage())
                    .collect(Collectors.joining(", "));

            throw new OrderException("Order failed due to: " + errorMessage);
        }
        Order order=new Order();
        order.setUserId(userId);
        order.setOrderedAt(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems=cart.getItems().stream()
                .map(item-> {
                    OrderItem orderItem=new OrderItem();
                    orderItem.setProductId(item.getProductId());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setPrice(item.getPrice());
                    orderItem.setOrder(order);
                    return orderItem;
                }).toList();

     order.setOrderItems(orderItems);

     order.setTotalAmount(cart.getTotalPrice());
        Order saved=orderRepository.save(order);

     inventoryClient.reduceStockWithResilience(requests,warehouseId);

        order.setOrderStatus(OrderStatus.SUCCESS);
        orderRepository.save(saved);
       cartClient.deleteCart(userId);
       return new OrderResponse(saved);

        //payment service later

    }



    @Override
    public OrderResponse findOrderByOrderId(Long userId, Long orderId) {

        return ;
    }

    @Override
    public Pageable findAllOrder(Long userId, int pageNo, int size) {
        return null;
    }


}
