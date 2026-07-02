package com.shopit.shop.DTO;

import com.shopit.shop.Entity.Cart;
import com.shopit.shop.Entity.Product;
import com.shopit.shop.Entity.User;

import java.util.List;

public record CartDTO(UserDTO customer, List<ProductDTO> products) {
    public static CartDTO toDTO(Cart cart) {
        UserDTO customerDTO = cart.getCustomer() != null
                ? UserDTO.toDTO(cart.getCustomer())
                : null;

        List<ProductDTO> productDTOs = cart.getProducts() != null
                ? cart.getProducts().stream()
                .map(ProductDTO::toDTO)
                .toList()
                : List.of();

        return new CartDTO(customerDTO, productDTOs);
    }

    public Cart toEntity(CartDTO dto) {
        User customer = dto.customer() != null
                ? UserDTO.toEntity(dto.customer())
                : null;

        List<Product> products = dto.products() != null
                ? dto.products().stream()
                .map(ProductDTO::toEntity)
                .toList()
                : List.of();

        return Cart.builder()
                .customer(customer)
                .products(products)
                .build();
    }
}
