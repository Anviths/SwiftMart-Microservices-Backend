package com.jsp.swiftmart.order_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem {

    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
