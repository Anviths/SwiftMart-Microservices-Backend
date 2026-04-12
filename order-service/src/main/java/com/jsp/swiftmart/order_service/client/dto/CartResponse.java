package com.jsp.swiftmart.order_service.client.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartResponse {

    private Long userId;
    private List<CartItemDto> items;
    private Double totalPrice;
    private boolean isActive;
}
