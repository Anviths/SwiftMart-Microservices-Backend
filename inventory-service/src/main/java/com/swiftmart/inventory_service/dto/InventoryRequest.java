package com.swiftmart.inventory_service.dto;

import lombok.Data;

@Data
public class InventoryRequest {
    private Long productId;
    private Integer quantity;
    private Long warehouseId;
}

