package com.shopit.shop.DTO;

import com.shopit.shop.Entity.Order;
import com.shopit.shop.Entity.Product;
import com.shopit.shop.Entity.Types.OrderStatus;
import com.shopit.shop.Entity.Types.PaymentMethod;
import com.shopit.shop.Entity.Types.PaymentStatus;
import com.shopit.shop.Entity.Types.ShippingStatus;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record OrderDTO(
        @Pattern(regexp = "PENDING|COMPLETED|CANCELLED", message = "Status must be PENDING or COMPLETED or CANCELLED.")
        OrderStatus status,
        @Pattern(regexp = "PAID|UNPAID", message = "Payment Status must be PAID or UNPAID.")
        PaymentStatus paymentStatus,
        @Pattern(regexp = "PACKING|PENDING|SHIPPED|DELIVERED", message = "Shipping Status must be PACKING or PENDING or SHIPPED or DELIVERED.")
        ShippingStatus shippingStatus,
        @Pattern(regexp = "CASH|CREDIT_CARD|PAYPAL", message = "Payment Method must be CASH or CREDIT CARD or PAYPAL.")
        PaymentMethod paymentMethod,
        List<ProductDTO> products) {

    public static OrderDTO toDTO(Order order) {
        List<ProductDTO> productDTOs = order.getProducts() != null
                ? order.getProducts().stream()
                .map(ProductDTO::toDTO)
                .toList()
                : List.of();

        return new OrderDTO(
                order.getStatus(),
                order.getPaymentStatus(),
                order.getShippingStatus(),
                order.getPaymentMethod(),
                productDTOs
        );
    }

    public static Order toEntity(OrderDTO dto) {
        List<Product> products = dto.products() != null
                ? dto.products().stream()
                .map(ProductDTO::toEntity)
                .toList()
                : List.of();

        Order order = new Order();
        order.setStatus(dto.status());
        order.setPaymentStatus(dto.paymentStatus());
        order.setShippingStatus(dto.shippingStatus());
        order.setPaymentMethod(dto.paymentMethod());
        order.setProducts(products);
        return order;
    }
}
