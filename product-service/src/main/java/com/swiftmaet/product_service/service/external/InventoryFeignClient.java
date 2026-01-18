package com.swiftmaet.product_service.service.external;

import com.swiftmaet.product_service.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryFeignClient {

    @GetMapping("/swiftmart/inventory/{productId}")
    List<InventoryResponse> getInventory(@PathVariable Long productId);
}
