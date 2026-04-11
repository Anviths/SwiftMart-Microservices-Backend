package com.swiftmart.inventory_service.client;

import com.swiftmart.inventory_service.exception.InventoryException;
import org.springframework.stereotype.Component;

@Component
public class ProductFallBack implements ProductClient{

    @Override
    public Boolean checkProduct(Long productId) {
       throw new InventoryException("product service is down");
    }
}
