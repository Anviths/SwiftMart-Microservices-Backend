package com.swiftmart.inventory_service.controller;

import com.swiftmart.inventory_service.dto.InventoryRequest;
import com.swiftmart.inventory_service.dto.InventoryResponse;
import com.swiftmart.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/swiftmart/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    @PostMapping
    public ResponseEntity<InventoryResponse> createStock(@RequestBody InventoryRequest inventoryRequest){
       return ResponseEntity
               .status(HttpStatus.CREATED)
               .body( inventoryService.addInventory(inventoryRequest));
    }
    @PutMapping("/{productId}/{warehouseId}/increase")
    public ResponseEntity<InventoryResponse> increaseStock(@PathVariable long productId,@PathVariable long warehouseId, @RequestParam int quantity){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body( inventoryService.increaseStock(productId,warehouseId,quantity));
    }
    @PutMapping("/{productId}/{warehouseId}/decrease")
    public ResponseEntity<InventoryResponse> decreaseStock(@PathVariable Long productId,@PathVariable Long warehouseId,int quantity){
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body( inventoryService.decreaseStock(productId,warehouseId,quantity));
    }

    @GetMapping("/{productId}/{warehouseId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable long productId,@PathVariable long warehouseId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( inventoryService.getInventory(productId,warehouseId));
    }

    @GetMapping("/low-stock/{warehouseId}")
    public ResponseEntity<List<InventoryResponse>> lowStock(@PathVariable  long warehouseId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( inventoryService.lowStock(warehouseId));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<List<InventoryResponse>> getAllInventoryByProductId(@PathVariable long productId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body( inventoryService.getAllInventoryByProductId(productId));
    }
}
