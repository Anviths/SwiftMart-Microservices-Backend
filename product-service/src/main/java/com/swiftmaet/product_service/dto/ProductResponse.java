package com.swiftmaet.product_service.dto;

import com.swiftmaet.product_service.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductResponse {

    private Long productId;
    private String productName;
    private Double price;
    private String description;
    private String category;
    private List<String> productImage;

    public static ProductResponse of(Product product){
      return   ProductResponse.builder()
              .productId(product.getProductId())
              .productName(product.getProductName())
              .description(product.getDescription())
              .price(product.getPrice())
              .productImage(product.getImage().stream().map(x-> x.getImageAddress()).toList())
              .category(product.getCategory().getName())
              .build();



    }
}
