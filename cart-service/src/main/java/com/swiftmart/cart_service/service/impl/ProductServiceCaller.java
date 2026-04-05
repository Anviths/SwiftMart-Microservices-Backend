package com.swiftmart.cart_service.service.impl;

import com.swiftmart.cart_service.client.ProductClient;
import com.swiftmart.cart_service.client.dto.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceCaller {
    private final ProductClient productClient;

    @Retry(name = "productService")
    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackProduct")
    public ProductResponse getProduct(Long productId){
        System.out.println("product service");
        return productClient.getProduct(productId);
    }
    public ProductResponse fallbackProduct(Long productId, Exception ex) {

        System.out.println("Fallback triggered: " + ex.getMessage());

        throw new RuntimeException("Product service temporarily unavailable");
    }
}
