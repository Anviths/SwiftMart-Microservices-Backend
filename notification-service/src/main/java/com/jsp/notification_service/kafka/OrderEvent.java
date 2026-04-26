package com.jsp.notification_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
