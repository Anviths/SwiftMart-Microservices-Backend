package com.swiftmaet.product_service.service;

import com.swiftmaet.product_service.dto.CategoryRequest;
import com.swiftmaet.product_service.dto.CategoryResponse;
import com.swiftmaet.product_service.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest category);
    CategoryResponse updateCategory(CategoryRequest category,Long categoryId);
    CategoryResponse findById(Long id);
    CategoryResponse findByName(String name);
    List<CategoryResponse> findAll();

}
