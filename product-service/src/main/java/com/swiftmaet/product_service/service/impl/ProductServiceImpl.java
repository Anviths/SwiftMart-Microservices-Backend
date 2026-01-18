package com.swiftmaet.product_service.service.impl;


import com.swiftmaet.product_service.dao.CategoryRepository;
import com.swiftmaet.product_service.dao.ProductImageRepository;
import com.swiftmaet.product_service.dao.ProductRepository;
import com.swiftmaet.product_service.dto.InventoryResponse;
import com.swiftmaet.product_service.dto.ProducRequest;
import com.swiftmaet.product_service.dto.ProductInventoryResponse;
import com.swiftmaet.product_service.dto.ProductResponse;
import com.swiftmaet.product_service.entity.Category;
import com.swiftmaet.product_service.entity.Product;
import com.swiftmaet.product_service.entity.ProductImage;
import com.swiftmaet.product_service.exception.ProductNotFoundException;
import com.swiftmaet.product_service.service.ProductService;
import com.swiftmaet.product_service.service.external.InventoryFeignClient;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final InventoryFeignClient inventoryClient;

    @Transactional
    @Override
    public ProductResponse addProduct(ProducRequest producRequest) {

       Category category= categoryRepository.findByName(producRequest.getCategory())
               .orElseGet(()->{Category newCategory=
                       Category.builder()
                       .name(producRequest.getCategory())
                   .build();
                 return categoryRepository.save(newCategory);
               });

             List<ProductImage> productImages=producRequest.getProductImage()
                     .stream().map(img->ProductImage.builder()
                             .imageAddress(img)
                             .build()).toList();




       Product product= Product.builder()
                .productName(producRequest.getProductName())
                .price(producRequest.getPrice())
                .image(productImages)
                .description(producRequest.getDescription())
                .category(category)
                .build();

       Product savedproduct= productRepository.save(product);


        return ProductResponse.of(savedproduct);
    }

    @Override
    public ProductResponse findProductById(long productId) {
       Product product= productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product not found with id: " + productId));
       return ProductResponse.of(product);
    }

    @Transactional
    @Override
    public void deleteProduct(long productId) {
       Product product= productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Product not found with id: " + productId));

       Category category=product.getCategory();
        category.getProducts().remove(product);
        categoryRepository.save(category);
        productImageRepository.deleteAll(product.getImage());
        productRepository.delete(product);
         productRepository.delete(product);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(ProducRequest producRequest,Long productId) {
        Product product=productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("Product not found with id: " + productId));

        Category category= categoryRepository.findByName(producRequest.getCategory())
                .orElseGet(()->{Category newCategory=
                        Category.builder()
                                .name(producRequest.getCategory())
                                .build();
                    return categoryRepository.save(newCategory);
                });


        product.setProductName(producRequest.getProductName());
        product.setPrice(producRequest.getPrice());
        product.setDescription(producRequest.getDescription());
        product.setCategory(category);

        return ProductResponse.of(productRepository.save(product));

    }

    @Override
    public List<ProductResponse> findAllProducts() {

       return productRepository.findAll().stream().map(ProductResponse::of).toList();


    }

    @Override
    public Page<ProductResponse> getAllProducts(int size, int page) {

        Pageable  pageable= PageRequest.of(page,size);

        return productRepository.findAll(pageable)
                .map(ProductResponse::of);
    }




    @Override
    public List<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ProductResponse> findAllProductsByCategory(String category) {
        return productRepository.findByCategory_name(category).stream()
                .map(ProductResponse::of).toList();
    }

    @Override
    public List<ProductResponse> findAllProductsByCategory(String category, Pageable pageable) {
        return List.of();
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return List.of(); // graceful degradation
        }
        return productRepository.searchByKeyword(keyword.toLowerCase())
                .stream()
                .map(ProductResponse::of)
                .toList();
    }

    @Override
    public List<ProductResponse> filterByPrice(Double min, Double max) {
        double minPrice = min != null ? min : 0.0;
        double maxPrice = max != null ? max : Double.MAX_VALUE;

        if (minPrice > maxPrice) {
            throw new IllegalArgumentException("min price cannot exceed max price");
        }

        return productRepository.findByPriceBetween(minPrice, maxPrice)
                .stream()
                .map(ProductResponse::of)
                .toList();
    }

    @Override
    public ProductInventoryResponse getProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
//     /feign client
       List< InventoryResponse> inventory =
                inventoryClient.getInventory(productId);
        return new ProductInventoryResponse(product,inventory);

//        Rest Template
//        InventoryResponse inventory =
//                restTemplate.getForObject(
//                        "http://inventory-service/inventory/{productId}",
//                        InventoryResponse.class,
//                        productId
//                );
    }


    private Category getOrCreateCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .orElseGet(() -> categoryRepository.save(
                        Category.builder().name(categoryName).build()
                ));
    }





}
