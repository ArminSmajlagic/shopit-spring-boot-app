package com.shopit.shop.Controller;

import com.shopit.shop.DTO.ProductDTO;
import com.shopit.shop.Entity.Types.Category;
import com.shopit.shop.Entity.Product;
import com.shopit.shop.Exception.NotFoundException;
import com.shopit.shop.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) throws NotFoundException {
        var products = service.getProducts(
                page,
                size,
                sortBy,
                direction
        );

        ArrayList<ProductDTO> result = new ArrayList<>(size);

        for (var product: products){
            result.add(ProductDTO.toDTO(product));
        }

        if(result.isEmpty())
            throw new NotFoundException("No products found");

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(
            @PathVariable Long id
    ) throws NotFoundException {
        try {
            var result = service.getById(id);

            if(result == null)
                throw new NotFoundException("No products found for this id");

            return ResponseEntity.ok(result);
        }catch (Exception e){
            throw new NotFoundException("No products found for this id");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws NotFoundException {
        var result =  service.search(
                keyword,
                page,
                size
        );

        if(result.isEmpty())
            throw new NotFoundException("No products found for this keyword");

        return ResponseEntity.ok(result);
    }

    @GetMapping("/category/{category}")
    public  ResponseEntity<Page<Product>> getByCategory(
            @PathVariable Category category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws NotFoundException {
        var result =  service.filterByCategory(
                category,
                page,
                size
        );

        if(result.isEmpty())
            throw new NotFoundException("No products found for this category");

        return ResponseEntity.ok(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(
            @RequestBody Product product
    ) {
        return service.create(product);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestBody Product product
    ) {
        var result =  service.update(id, product);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        service.delete(id);
    }
}