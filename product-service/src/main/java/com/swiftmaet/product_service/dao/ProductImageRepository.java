package com.swiftmaet.product_service.dao;

import com.swiftmaet.product_service.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage ,Long> {
}
