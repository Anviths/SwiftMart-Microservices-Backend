package com.swiftmart.inventory_service.repository;

import com.swiftmart.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProductId(Long productId);
    Optional<Inventory> findByProductIdAndWarehouseId(
            Long productId,
            Long warehouseId
    );
    Optional<Inventory> findByWarehouseIdAndAvailableQuantityLessThan(long warehouseId,int quantity);
}

