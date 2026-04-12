package com.swiftmart.cart_service.controller;

import com.swiftmart.cart_service.dto.AddToCartRequest;
import com.swiftmart.cart_service.dto.CartResponse;
import com.swiftmart.cart_service.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.DeleteExchange;

@RequiredArgsConstructor
@RestController
@RequestMapping("/swiftmart/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<CartResponse> addToCart(@RequestParam Long userId,@RequestBody AddToCartRequest cartRequest){
         return ResponseEntity.ok(cartService.addToCart(userId,cartRequest));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<CartResponse> removeItemFromCart(@RequestParam Long userId,@PathVariable Long productId){
        return ResponseEntity.ok(cartService.removeFromCart(userId,productId));
    }

    @PutMapping("/increase/{productId}")
    public ResponseEntity<CartResponse> increaseItemFromCart(@RequestParam Long userId,@PathVariable Long productId){
        return ResponseEntity.ok(cartService.increaseQuantity(userId,productId));
    }

    @PutMapping("/decrease/{productId}")
    public ResponseEntity<CartResponse> decreaseItemFromCart(@RequestParam Long userId,@PathVariable Long productId){
        return ResponseEntity.ok(cartService.decreaseQuantity(userId,productId));
    }

    @DeleteMapping("/delete")
   public ResponseEntity<String> deleteCart(@RequestParam Long userId){
        cartService.deleteCart(userId);
        return ResponseEntity.ok("Success");

    }
}
