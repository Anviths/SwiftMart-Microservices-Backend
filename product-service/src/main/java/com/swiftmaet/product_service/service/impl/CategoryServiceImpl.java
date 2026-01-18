package com.swiftmaet.product_service.service.impl;

import com.swiftmaet.product_service.dao.CategoryRepository;
import com.swiftmaet.product_service.dto.CategoryRequest;
import com.swiftmaet.product_service.dto.CategoryResponse;
import com.swiftmaet.product_service.entity.Category;
import com.swiftmaet.product_service.entity.ProductImage;
import com.swiftmaet.product_service.exception.CategoryException;
import com.swiftmaet.product_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        if(categoryRepository.existsByName(categoryRequest.getName())){
            throw new IllegalArgumentException("category already created");
        }
        ProductImage productImage = new ProductImage();
        productImage.setImageAddress(categoryRequest.getCategoryImageURL());
        Category category= Category
                .builder().
                name(categoryRequest.getName())
                .categoryImage(productImage)
                .build();
       Category  savedCategory= categoryRepository.save(category);
        return new CategoryResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest categoryRequest,Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("category not found"));
        category.setName(categoryRequest.getName());


        Category updateCategory=  categoryRepository.save(category);

        return new CategoryResponse(updateCategory);
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepository.findById(id)
                .map( CategoryResponse::new)
                .orElseThrow(()->new CategoryException("Category not found "+id));
    }

    @Override
    public CategoryResponse findByName(String name) {
        return categoryRepository.findByName(name)
                .map(CategoryResponse::new)
                .orElseThrow(()->new CategoryException("Category not found "+name));

    }

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(CategoryResponse::new).toList();
    }
}
