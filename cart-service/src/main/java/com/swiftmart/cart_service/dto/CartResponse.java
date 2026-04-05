package com.swiftmart.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CartResponse {
    private Long userId;
    private List<CartItemDto> items;
    private Double totalPrice;
}
