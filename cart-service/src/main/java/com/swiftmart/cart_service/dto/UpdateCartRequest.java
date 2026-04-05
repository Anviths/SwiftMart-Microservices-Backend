package com.swiftmart.cart_service.dto;

import lombok.Data;

@Data
public class UpdateCartRequest {
    private Long productId;
    private Integer quantity;
}
