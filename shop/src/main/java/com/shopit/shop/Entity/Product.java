package com.shopit.shop.Entity;

import com.shopit.shop.Entity.Types.Category;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseAuditEntity<Long> {

    private String name;

    @Column(length = 1000)
    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    private Category category;
}
