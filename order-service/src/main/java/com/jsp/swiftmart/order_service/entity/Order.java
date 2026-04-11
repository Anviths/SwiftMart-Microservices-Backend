package com.jsp.swiftmart.order_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private LocalDateTime orderedAt;
    private OrderStatus orderStatus;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "order")
    private List<OrderItem> orderItems=new ArrayList<>();

}
