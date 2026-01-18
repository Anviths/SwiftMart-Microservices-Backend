package com.swiftmart.inventory_service.service.impl;

import com.swiftmart.inventory_service.dto.InventoryRequest;
import com.swiftmart.inventory_service.dto.InventoryResponse;
import com.swiftmart.inventory_service.entity.Inventory;
import com.swiftmart.inventory_service.exception.InventoryException;
import com.swiftmart.inventory_service.repository.InventoryRepository;
import com.swiftmart.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    @Override
    public InventoryResponse addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepository
                .findByProductIdAndWarehouseId(
                        inventoryRequest.getProductId(),
                        inventoryRequest.getWarehouseId()
                )
                .orElseGet(() -> {
                    Inventory inv = new Inventory();
                    inv.setProductId(inventoryRequest.getProductId());
                    inv.setWarehouseId(inventoryRequest.getWarehouseId());
                    inv.setAvailableQuantity(0);
                    inv.setReservedQuantity(5);
                    return inv;
                });
        inventory.setAvailableQuantity(
                inventory.getAvailableQuantity() + inventoryRequest.getQuantity()
        );
        return new InventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    @Transactional
    public InventoryResponse increaseStock(long productId, long wareHouseId,int quantity) {
       Inventory inventory= inventoryRepository.findByProductIdAndWarehouseId(productId,wareHouseId).orElseThrow(()->new InventoryException("Inventory not found"));
       inventory.setAvailableQuantity(inventory.getAvailableQuantity()+quantity);

        return new InventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    @Transactional
    public InventoryResponse decreaseStock(long productId, long wareHouseId, int quantity) {
        Inventory inventory= inventoryRepository.findByProductIdAndWarehouseId(productId,wareHouseId).orElseThrow(()->new InventoryException("Inventory not found"));
        if(inventory.getAvailableQuantity()<quantity) throw new InventoryException("Insufficient stock");

        inventory.setAvailableQuantity(inventory.getAvailableQuantity()-quantity);

        return new InventoryResponse(inventoryRepository.save(inventory));
    }

    @Override
    @Transactional
    public List<InventoryResponse> lowStock( long wareHouseId) {
        return inventoryRepository
                .findByWarehouseIdAndAvailableQuantityLessThan(wareHouseId, 5)
                .stream()
                .map(InventoryResponse::new)
                .toList();
    }

    @Override
    public InventoryResponse getInventory(Long productId, Long wareHouseId) {
       return inventoryRepository
               .findByProductIdAndWarehouseId(productId,wareHouseId)
               .map(InventoryResponse::new)
               .orElseThrow(()->new InventoryException("Inventory not found"));



    }

    @Override
    public List<InventoryResponse> getAllInventoryByProductId(long productId) {
        return inventoryRepository.findByProductId(productId).stream().map(InventoryResponse::new).toList();
    }

    @Transactional
    @Override
    public void  deleteInventory(long productId, long warehouseId) {
        Inventory inventory = inventoryRepository
                .findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new InventoryException("Inventory not found"));

        inventoryRepository.delete(inventory);

    }


}
