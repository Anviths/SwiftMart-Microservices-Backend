package com.swiftmart.cart_service.client;

import com.swiftmart.cart_service.client.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "PRODUCT-SERVICE", fallback = ProductFallback.class)
public interface ProductClient {

    @GetMapping("/swiftmart/products/{productId}")
    ProductResponse getProduct(@PathVariable Long productId);
}
