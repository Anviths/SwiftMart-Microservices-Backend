package com.swiftmart.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockCheckResponse {

    private Long productId;
    private boolean inStock;
    private String message;
}
