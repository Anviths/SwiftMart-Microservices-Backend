package com.swiftmart.cart_service.client;

import com.swiftmart.cart_service.client.dto.ProductResponse;
import com.swiftmart.cart_service.exception.ProductServiceException;
import org.springframework.stereotype.Component;

@Component
public class ProductFallback implements ProductClient {

    @Override
    public ProductResponse getProduct(Long productId) {
        throw new ProductServiceException("Product service unavailable");
    }
}
