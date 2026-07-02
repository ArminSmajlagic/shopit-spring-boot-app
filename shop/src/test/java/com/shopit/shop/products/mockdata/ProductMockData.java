package com.shopit.shop.products.mockdata;
import com.shopit.shop.products.dto.Category;
import com.shopit.shop.products.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public class ProductMockData {

    public static List<ProductDto> getProducts() {
        return List.of(
                new ProductDto(
                        1L,
                        "Samsung Galaxy S25",
                        "Latest Samsung flagship smartphone",
                        new BigDecimal("999.99"),
                        25,
                        Category.ELECTRONICS
                ),
                new ProductDto(
                        2L,
                        "Apple AirPods Pro",
                        "Wireless noise-cancelling earbuds",
                        new BigDecimal("249.99"),
                        40,
                        Category.ELECTRONICS
                ),
                new ProductDto(
                        3L,
                        "Nike Running Shoes",
                        "Comfortable running shoes for daily training",
                        new BigDecimal("129.99"),
                        60,
                        Category.SPORTS
                ),
                new ProductDto(
                        4L,
                        "Men's Hoodie",
                        "Cotton blend hoodie",
                        new BigDecimal("49.99"),
                        75,
                        Category.CLOTHING
                ),
                new ProductDto(
                        5L,
                        "Coffee Maker",
                        "12-cup programmable coffee machine",
                        new BigDecimal("89.99"),
                        20,
                        Category.HOME_AND_KITCHEN
                ),
                new ProductDto(
                        6L,
                        "Clean Code",
                        "A Handbook of Agile Software Craftsmanship",
                        new BigDecimal("34.99"),
                        100,
                        Category.BOOKS
                ),
                new ProductDto(
                        7L,
                        "LEGO City Fire Station",
                        "Building set for children aged 6+",
                        new BigDecimal("79.99"),
                        30,
                        Category.TOYS
                ),
                new ProductDto(
                        8L,
                        "Vitamin C Serum",
                        "Facial serum for brighter skin",
                        new BigDecimal("19.99"),
                        50,
                        Category.BEAUTY
                ),
                new ProductDto(
                        9L,
                        "Protein Powder",
                        "Whey protein chocolate flavor",
                        new BigDecimal("54.99"),
                        35,
                        Category.GROCERY
                ),
                new ProductDto(
                        10L,
                        "Gaming Keyboard",
                        "Mechanical RGB keyboard",
                        new BigDecimal("119.99"),
                        15,
                        Category.ELECTRONICS
                )
        );
    }
}