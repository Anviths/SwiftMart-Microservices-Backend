package com.jsp.swiftmart.order_service.kafka;

import com.jsp.swiftmart.order_service.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {

    private Long orderId;
//    private List<OrderItem> items;
    private Double amount;
    private String status;
}
