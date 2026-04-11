package com.jsp.swiftmart.order_service.client;

import com.jsp.swiftmart.order_service.dto.InventoryRequest;
import com.jsp.swiftmart.order_service.dto.StockCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {
    @PostMapping("/swiftmart/inventory/check/{warehouseId}")
    List<StockCheckResponse> checkStock(@RequestBody List<InventoryRequest> requests,@PathVariable Long warehouseId);

    @PostMapping("/swiftmart/inventory/reduce/{warehouseId}")
    void reduceStock(@RequestBody List<InventoryRequest> requests,@PathVariable Long warehouseId);
}
