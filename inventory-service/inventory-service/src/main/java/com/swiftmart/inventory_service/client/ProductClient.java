package com.swiftmart.inventory_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "PRODUCT-SERVICE" ,fallback = ProductFallBack.class)
public interface ProductClient {

    @GetMapping("/swiftmart/products/check/{productId}")
     Boolean checkProduct(@PathVariable Long productId) ;



}
