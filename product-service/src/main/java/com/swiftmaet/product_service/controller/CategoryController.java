package com.swiftmaet.product_service.controller;

import com.swiftmaet.product_service.dto.CategoryRequest;
import com.swiftmaet.product_service.dto.CategoryResponse;
import com.swiftmaet.product_service.entity.Category;
import com.swiftmaet.product_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("swiftmart/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody @Valid CategoryRequest category){
        return  ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));

    }
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategory(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long categoryId){
        return ResponseEntity.ok(categoryService.findById(categoryId));
    }

    @GetMapping("findbyname/{categoryName}")
    public ResponseEntity<CategoryResponse> getCategoryByName(@PathVariable String categoryName){
        return ResponseEntity.ok(categoryService.findByName(categoryName));
    }
}
