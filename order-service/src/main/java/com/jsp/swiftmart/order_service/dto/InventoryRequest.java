package com.jsp.swiftmart.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryRequest {
    private Long productId;
    private Integer quantity;
}
