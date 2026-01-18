package com.swiftmaet.product_service.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.util.List;

@Data
public class ProducRequestCsv {

    @CsvBindByName
    private String productName;
    @CsvBindByName
    private Double price;
    @CsvBindByName
    private String description;
    @CsvBindByName
    private Integer stock;
    @CsvBindByName
    private String category;

}
