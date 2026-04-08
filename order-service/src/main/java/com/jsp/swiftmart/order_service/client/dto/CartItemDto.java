package com.jsp.swiftmart.order_service.client.dto;

import lombok.Data;

@Data
public class CartItemDto {

    private Long productId;
    private Integer quantity;
    private Double price;
}
