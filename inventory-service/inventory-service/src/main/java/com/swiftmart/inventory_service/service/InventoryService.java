package com.swiftmart.inventory_service.service;

import com.swiftmart.inventory_service.dto.InventoryRequest;
import com.swiftmart.inventory_service.dto.InventoryResponse;
import com.swiftmart.inventory_service.dto.StockCheckResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface InventoryService {

    InventoryResponse addInventory(InventoryRequest inventoryRequest);
    InventoryResponse increaseStock(long productId,long wareHouseId,int quantity);
    InventoryResponse decreaseStock(long productId, long wareHouseId,int quantity);
    List<InventoryResponse> lowStock( long wareHouseId);
    void deleteInventory(long productId, long wareHouseId);

    InventoryResponse getInventory(Long productId, Long warehouseId);

   List< InventoryResponse> getAllInventoryByProductId(long productId);

    List<StockCheckResponse> checkStock(List<InventoryRequest> requests,Long warehouseId);

    void reduceStock(List<InventoryRequest> requests,Long warehouseId);

     List<InventoryResponse> findAllByWareHouse(Long warehouseId);
}
