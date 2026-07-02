package com.shopit.shop.products.controller;

import com.shopit.shop.products.dto.Category;
import com.shopit.shop.products.Entity.Product;
import com.shopit.shop.products.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductService service;

    @GetMapping
    public Page<Product> getAll(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) {
        return (Page<Product>) service.getProducts(
                page,
                size,
                sortBy,
                direction
        );
    }

    @GetMapping("/{id}")
    public Product getById(
            @PathVariable Long id
    ) {
        return service.getById(id);
    }

    @GetMapping("/search")
    public Page<Product> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return (Page<Product>) service.search(
                keyword,
                page,
                size
        );
    }

    @GetMapping("/category/{category}")
    public Page<Product> getByCategory(
            @PathVariable Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return (Page<Product>) service.filterByCategory(
                category,
                page,
                size
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(
            @RequestBody Product product
    ) {
        return service.create(product);
    }

    @PutMapping("/{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody Product product
    ) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }
}