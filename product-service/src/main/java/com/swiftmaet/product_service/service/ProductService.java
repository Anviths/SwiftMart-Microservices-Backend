package com.swiftmaet.product_service.service;

import com.swiftmaet.product_service.dto.ProducRequest;
import com.swiftmaet.product_service.dto.ProductInventoryResponse;
import com.swiftmaet.product_service.dto.ProductResponse;
import com.swiftmaet.product_service.entity.Category;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    ProductResponse addProduct(ProducRequest producRequest);

    ProductResponse findProductById(long id);

    void deleteProduct(long id);

    ProductResponse updateProduct(ProducRequest producRequest,Long productId);

    List<ProductResponse> findAllProducts();


    Page getAllProducts(int size, int page);
    List<Category> findAllCategory();

    List<ProductResponse> findAllProductsByCategory(String category);

    List<ProductResponse> findAllProductsByCategory(String category, Pageable pageable);


     List<ProductResponse> searchProducts(String q);

     List<ProductResponse> filterByPrice(Double min, Double max);

    ProductInventoryResponse getProduct(Long productId);
}
