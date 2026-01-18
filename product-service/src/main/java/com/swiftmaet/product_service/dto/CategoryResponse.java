package com.swiftmaet.product_service.dto;

import com.swiftmaet.product_service.entity.Category;
import com.swiftmaet.product_service.entity.ProductImage;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;

@Data
public class CategoryResponse {

    private Long categoryId;
    private String name;
    private String categoryImage;

    public CategoryResponse(Category category){
       this.categoryId=category.getCategoryId();
       this. name=category.getName();
       if(category.getCategoryImage()!=null)
       this. categoryImage=category.getCategoryImage().getImageAddress();
    }
}
