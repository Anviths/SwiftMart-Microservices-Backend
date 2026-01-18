package com.swiftmaet.product_service.dao;

import com.swiftmaet.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByCategory_name(String category);
    Page<Product> findAll(Pageable pageable);

    @Query("""
   SELECT p FROM Product p
   WHERE LOWER(p.productName) LIKE %:keyword%
      OR LOWER(p.description) LIKE %:keyword%
""")
    List<Product> searchByKeyword(@Param("keyword") String keyword);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);
}
