package com.swiftmaet.product_service.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class ProducRequest {

    private String productName;
    private Double price;
    private String description;
    private String category;
    private List<String> productImage;



}
