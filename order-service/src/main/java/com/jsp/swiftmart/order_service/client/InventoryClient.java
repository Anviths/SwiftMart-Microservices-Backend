package com.jsp.swiftmart.order_service.client;

import com.jsp.swiftmart.order_service.dto.InventoryRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryClient {
    @PostMapping("/swiftmart/inventory/check")
    Boolean checkStock(List<InventoryRequest> requests);

    @PostMapping("/swiftmart/inventory/reduce")
    void reduceStock(List<InventoryRequest> requests);
}
