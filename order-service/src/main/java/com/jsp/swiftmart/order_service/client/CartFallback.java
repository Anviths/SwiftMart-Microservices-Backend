package com.jsp.swiftmart.order_service.client;

import com.jsp.swiftmart.order_service.client.dto.CartResponse;
import com.jsp.swiftmart.order_service.exception.CartException;
import org.springframework.stereotype.Component;

@Component
public class CartFallback {

    public CartResponse getCart(){
        throw new CartException("cart service is down");
    }
}
