package com.shopit.shop.products.services;

import com.shopit.shop.products.dto.Category;
import com.shopit.shop.products.Entity.Product;
import com.shopit.shop.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Page<Product> getProducts(
            int page,
            int size,
            String sortBy,
            String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return repository.findAll(pageable);
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }

    public Product create(Product product) {
        product.setId(null);
        return repository.save(product);
    }

    public Product update(Long id, Product updated) {

        Product existing = getById(id);

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStockQuantity(updated.getStockQuantity());
        existing.setCategory(updated.getCategory());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Product> search(
            String keyword,
            int page,
            int size
    ) {
        return repository.findByNameContainingIgnoreCase(
                keyword,
                (java.awt.print.Pageable) PageRequest.of(page, size)
        );
    }

    public Page<Product> filterByCategory(
            Category category,
            int page,
            int size
    ) {
        return repository.findByCategory(
                category,
                (java.awt.print.Pageable) PageRequest.of(page, size)
        );
    }
}