package com.jsp.swiftmart.order_service.service.impl;

import com.jsp.swiftmart.order_service.client.InventoryClient;
import com.jsp.swiftmart.order_service.client.InventoryFallback;
import com.jsp.swiftmart.order_service.dto.InventoryRequest;
import com.jsp.swiftmart.order_service.dto.StockCheckResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CheckStockWithResilience {

    private static final Logger log = LoggerFactory.getLogger(CheckStockWithResilience.class);

    private final InventoryClient inventoryClient;

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackCheckStock")
    @Retry(name = "inventoryService")
    public List<StockCheckResponse> checkStockWithResilience(
            List<InventoryRequest> requests, Long warehouseId) {

        return inventoryClient.checkStock(requests, warehouseId);
    }

    @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackReduceStock")
    @Retry(name = "inventoryService")
    public void reduceStockWithResilience(List<InventoryRequest> requests, Long warehouseId) {
        inventoryClient.reduceStock(requests, warehouseId);
    }


    public List<StockCheckResponse> fallbackCheckStock(
            List<InventoryRequest> requests,
            Long warehouseId,
            Throwable ex) {

        log.error("Fallback triggered due to: {}", ex.getMessage());

        return requests.stream()
                .map(r -> new StockCheckResponse(
                        r.getProductId(),
                        false,
                        "Service unavailable"
                ))
                .toList();
    }

    public void fallbackReduceStock(
            List<InventoryRequest> requests,
            Long warehouseId,
            Throwable ex) {

        log.error("Stock reduction failed: {}", ex.getMessage());
        throw new RuntimeException("Stock update failed. Please retry.");
    }
}
