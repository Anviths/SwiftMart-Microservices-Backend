package com.swiftmart.inventory_service.dto;

import com.swiftmart.inventory_service.entity.Inventory;
import lombok.Data;

@Data
public class InventoryResponse {
    private Long inventoryId;
    private Long productId;
    private Integer availableQuantity;
    private Long warehouseId;
    public InventoryResponse(Inventory inventory){
        this.inventoryId=inventory.getId();
        this.productId=inventory.getProductId();
        this.availableQuantity=inventory.getAvailableQuantity();
        this.warehouseId=inventory.getWarehouseId();
    }
}

