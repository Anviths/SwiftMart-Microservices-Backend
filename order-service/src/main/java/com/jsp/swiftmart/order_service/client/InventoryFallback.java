package com.jsp.swiftmart.order_service.client;

import com.jsp.swiftmart.order_service.dto.InventoryRequest;
import com.jsp.swiftmart.order_service.dto.StockCheckResponse;
import com.jsp.swiftmart.order_service.exception.InventoryServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class InventoryFallback implements  InventoryClient{

    private static final Logger log = LoggerFactory.getLogger(InventoryFallback.class);
    @Override
    public List<StockCheckResponse> checkStock(List<InventoryRequest> requests, Long warehouseId) {
        log.error("Inventory service fallback triggered");
     return   requests.stream().map(r->new StockCheckResponse(
               r.getProductId(),false,"Inventory service Not available"))
               .toList();
    }

    @Override
    public void reduceStock(List<InventoryRequest> requests, Long warehouseId) {
        log.error("Inventory service fallback triggered for warehouseId {}", warehouseId);
        throw new InventoryServiceException("Stock update failed. Please retry.");
    }
}
