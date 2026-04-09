package com.jsp.swiftmart.order_service.dto;

import com.jsp.swiftmart.order_service.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemResponse {
    private Long id;

    private Long productId;
    private Integer quantity;
    private Double price;
   public  OrderItemResponse(OrderItem orderItem){
       this.productId=orderItem.getProductId();
       this.quantity=orderItem.getQuantity();
       this.price=orderItem.getPrice();
   }
}
