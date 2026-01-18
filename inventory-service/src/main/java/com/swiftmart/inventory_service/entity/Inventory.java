package com.swiftmart.inventory_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"productId","warehouseId"})
)
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Integer availableQuantity;

    private Long warehouseId;

    private Integer reservedQuantity;

    @Version
    private Long version; // optimistic locking
}
