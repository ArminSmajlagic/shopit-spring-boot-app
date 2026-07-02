package com.shopit.shop.DTO;

import com.shopit.shop.Entity.Shop;
import jakarta.validation.constraints.NotBlank;

public record ShopDTO(
        @NotBlank(message = "Name can not be blank.")
        String name,
        @NotBlank(message = "Description can not be blank.")
        String description,
        @NotBlank(message = "Address can not be blank.")
        String address,
        @NotBlank(message = "Phone can not be blank.")
        String phone) {

    public static ShopDTO fromEntity(Shop shop) {
        return new ShopDTO(
                shop.getName(),
                shop.getDescription(),
                shop.getAddress(),
                shop.getPhone()
        );
    }

    public static Shop toEntity(ShopDTO dto) {
        Shop shop = new Shop();
        shop.setName(dto.name());
        shop.setDescription(dto.description());
        shop.setAddress(dto.address());
        shop.setPhone(dto.phone());
        return shop;
    }
}
