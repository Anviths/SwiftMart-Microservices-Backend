package com.swiftmart.inventory_service.service.impl;

import com.swiftmart.inventory_service.dto.InventoryRequest;
import com.swiftmart.inventory_service.dto.InventoryResponse;
import com.swiftmart.inventory_service.dto.StockCheckResponse;
import com.swiftmart.inventory_service.entity.Inventory;
import com.swiftmart.inventory_service.exception.InventoryException;
import com.swiftmart.inventory_service.repository.InventoryRepository;
import com.swiftmart.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public  List<StockCheckResponse> checkStock(List<InventoryRequest> requests,Long wareHouseId) {
        List<Long> productsId=requests.stream()
                        .map(InventoryRequest::getProductId).toList();

        List<Inventory> inventories= inventoryRepository.findAllByProductIdInAndWarehouseId(productsId,wareHouseId);

        Map<Long,Inventory> inventoryMap=inventories.stream()
                .collect(Collectors.toMap(Inventory::getProductId,i->i));

        List<StockCheckResponse> responseList = new ArrayList<>();

        for(InventoryRequest request:requests){
            Inventory inventory=inventoryMap.get(request.getProductId());

            if(inventory==null){
                responseList.add(new StockCheckResponse(
                        request.getProductId(),
                        false,
                        "Product Not Found"
                ));
                continue;
            }
            if(inventory.getAvailableQuantity()<request.getQuantity()){
                responseList.add(new StockCheckResponse(
                        request.getProductId(),
                        false,
                        "Out of stock"
                ));
            }else {
                responseList.add(new StockCheckResponse(
                        request.getProductId(),
                        true,
                        "Available"
                ));
            }
        }

        return responseList;
    }
    @Transactional
    @Override
    public void reduceStock(List<InventoryRequest> requests,Long wareHouseId) {
        // get all
        List<Long> productIds=requests.stream()
                        .map(InventoryRequest::getProductId).toList();


        Map<Long,Inventory> inventories = inventoryRepository.findAllByProductIdInAndWarehouseId(productIds,wareHouseId).stream()
                .collect(Collectors.toMap(Inventory::getProductId,i-> i));

        for(InventoryRequest req:requests) {
            Inventory inventory = inventories.get(req.getProductId());

            if (inventory == null) {
                throw new InventoryException("product not found " + req.getProductId());


            }

            if (inventory.getAvailableQuantity() < req.getQuantity()) {
                throw new InventoryException("Stock changed, insufficient for product: " + req.getProductId());

            }
        }
            for (InventoryRequest req : requests) {

                Inventory inventory = inventories.get(req.getProductId());

                inventory.setAvailableQuantity(
                        inventory.getAvailableQuantity() - req.getQuantity()
                );
            }
        inventoryRepository.saveAll(inventories.values());



    }

    @Override
    public List<InventoryResponse> findAllByWareHouse(Long warehouseId) {
        return inventoryRepository.findAllByWarehouseId(warehouseId).stream()
                .map(inv->new InventoryResponse(inv)).toList();
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
