package com.shopit.shop.Entity;

import com.shopit.shop.Entity.Types.OrderStatus;
import com.shopit.shop.Entity.Types.PaymentMethod;
import com.shopit.shop.Entity.Types.PaymentStatus;
import com.shopit.shop.Entity.Types.ShippingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long>{
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @ManyToMany
    private List<Product> products;
}
