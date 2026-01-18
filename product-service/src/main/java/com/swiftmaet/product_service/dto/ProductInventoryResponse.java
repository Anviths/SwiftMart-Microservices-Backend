package com.swiftmaet.product_service.dto;

import com.swiftmaet.product_service.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventoryResponse {
    private long productId;
    private String productName;
    private List<InventoryResponse> inventory;

    public ProductInventoryResponse (Product product,List<InventoryResponse> inventory){

        this.productId=product.getProductId();
        this.productName=product.getProductName();
        this.inventory=inventory;
    }

}
