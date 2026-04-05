package com.swiftmart.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemDto {

    private Long productId;
    private Integer quantity;
    private Double price;
}
