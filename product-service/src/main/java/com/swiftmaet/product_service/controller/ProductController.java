package com.swiftmaet.product_service.controller;

import com.swiftmaet.product_service.dto.InventoryResponse;
import com.swiftmaet.product_service.dto.ProducRequest;
import com.swiftmaet.product_service.dto.ProductInventoryResponse;
import com.swiftmaet.product_service.dto.ProductResponse;
import com.swiftmaet.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/swiftmart/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProducRequest producRequest) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(producRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProducRequest producRequest,@PathVariable Long productId) {

        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(producRequest,productId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProducts());
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<ProductResponse>> getProductByCategory( @PathVariable String categoryName) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAllProductsByCategory(categoryName));
    }
    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(size,page));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findById(@PathVariable long productId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.findProductById(productId));
    }


    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(
            @RequestParam String q) {

        return ResponseEntity.ok(productService.searchProducts(q));
    }

    @GetMapping("/filter/price")
    public ResponseEntity<List<ProductResponse>> filterByPrice(
            @RequestParam(required = false) Double min,
            @RequestParam(required = false) Double max) {

        return ResponseEntity.ok(productService.filterByPrice(min, max));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addall")
    public ResponseEntity<String> addProduct(@RequestParam MultipartFile file) {

        return null;

    }




    @GetMapping("/inventory/{productId}")
    public ResponseEntity<ProductInventoryResponse> getProduct(
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }


}
