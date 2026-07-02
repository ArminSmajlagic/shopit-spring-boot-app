package com.shopit.shop.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart extends BaseEntity<Long>{
    @ManyToOne
    @JoinColumn(name = "id")
    private User customer;
    @ManyToMany
    private List<Product> products;
}
