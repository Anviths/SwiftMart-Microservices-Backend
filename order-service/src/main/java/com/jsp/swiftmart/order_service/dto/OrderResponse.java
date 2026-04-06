package com.jsp.swiftmart.order_service.dto;

import com.jsp.swiftmart.order_service.entity.OrderItem;
import com.jsp.swiftmart.order_service.entity.OrderStatus;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderResponse {

    private Long orderId;
    private Double totalPrice;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;


    private List<OrderItemResponse> items ;
}
