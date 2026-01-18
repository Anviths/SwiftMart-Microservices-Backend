package com.swiftmaet.product_service.dto;

import lombok.Data;

@Data
public class InventoryResponse {
    private Long inventoryId;
    private Long productId;
    private Integer availableQuantity;
    private Long warehouseId;

//    public InventoryResponse(Inventory inventory){
//        this.inventoryId=inventory.getId();
//        this.productId=inventory.getProductId();
//        this.availableQuantity=inventory.getAvailableQuantity();
//        this.warehouseId=inventory.getWarehouseId();
//    }
}
