package com.jsp.swiftmart.order_service.controller;

import com.jsp.swiftmart.order_service.dto.OrderResponse;
import com.jsp.swiftmart.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swiftmart/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestParam Long userId,@RequestParam Long warehouseId){
        return ResponseEntity.ok(orderService.createOrder(userId,warehouseId));
    }
}
