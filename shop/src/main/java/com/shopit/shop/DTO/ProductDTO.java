package com.shopit.shop.DTO;

import com.shopit.shop.Entity.Product;
import com.shopit.shop.Entity.Types.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductDTO (
        @NotBlank
        @Size(max = 100, message = "Name must not exceed 100 characters.")
    String name,
        @NotBlank
        @Size(max = 1000, message = "Description too long.")
    String description,
        @NotBlank(message = "Category is required.")
        @Pattern(regexp = "ELECTRONICS|CLOTHING|BOOKS|HOME_AND_KITCHEN|SPORTS|BEAUTY|TOYS|GROCERY")
    Category category,
        @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Price must be a valid decimal number.")
    BigDecimal price,
        @Min(value=1, message="Quantity must be at least 1.")
    Integer stockQuantity
){
    public static ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStockQuantity(dto.stockQuantity());
        product.setCategory(dto.category());
        return product;
    }
}
