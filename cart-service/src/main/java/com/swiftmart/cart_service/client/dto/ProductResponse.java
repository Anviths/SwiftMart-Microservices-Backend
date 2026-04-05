package com.swiftmart.cart_service.client.dto;

import lombok.Data;

@Data
public class ProductResponse {

    private Long productId;
    private String productName;
    private Double price;
    private String description;
    private String category;
}
