package com.swiftmart.cart_service.service;

import com.swiftmart.cart_service.dto.AddToCartRequest;
import com.swiftmart.cart_service.dto.CartResponse;
import com.swiftmart.cart_service.dto.UpdateCartRequest;
import org.jspecify.annotations.Nullable;

public interface CartService {
     CartResponse addToCart(Long userId, AddToCartRequest request);
     CartResponse removeFromCart(Long userId,Long productId);
    CartResponse getCart(Long userId);
     CartResponse increaseQuantity(Long userId, Long productId);

     CartResponse decreaseQuantity(Long userId, Long productId);
}
