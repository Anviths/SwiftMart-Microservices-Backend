package com.jsp.swiftmart.order_service.client;

import com.jsp.swiftmart.order_service.client.dto.CartResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "CART-SERVICE",fallback = CartFallback.class)
public interface CartClient {

    @GetMapping("/swiftmart/cart")
    CartResponse findCartByUserId(@RequestParam Long userId);
}
