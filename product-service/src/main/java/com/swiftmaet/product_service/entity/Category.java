package com.swiftmaet.product_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(nullable = false,unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductImage categoryImage;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
