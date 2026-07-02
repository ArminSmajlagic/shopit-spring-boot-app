package com.shopit.shop.Controller;

import com.shopit.shop.DTO.CartDTO;
import com.shopit.shop.DTO.ProductDTO;
import com.shopit.shop.DTO.ShopDTO;
import com.shopit.shop.DTO.UserDTO;
import com.shopit.shop.Exception.NotFoundException;
import com.shopit.shop.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartDTO>> getAll(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) throws NotFoundException
    {
        ArrayList<CartDTO> result = new ArrayList<>(size);

        for(var cart: cartService.getCarts(page, size, sortBy, direction)){
            result.add(CartDTO.toDTO(cart));
        }

        if(result.isEmpty())
            throw new NotFoundException("No carts found");

        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartDTO> create(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok(CartDTO.toDTO(cartService.saveCart(cartDTO.toEntity(cartDTO))));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long id){
        cartService.deleteCart(id);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody CartDTO cartDTO){
        var existing = cartService.getCartById(id);
        var newCart = cartDTO.toEntity(cartDTO);

        existing.setCustomer(newCart.getCustomer());
        existing.setProducts(newCart.getProducts());

        cartService.updateCart(existing);
    }

    @PatchMapping(value = "/add")
    public void AddToCart(@RequestParam Long cartId, @RequestBody ProductDTO productDTO){
        var cart = cartService.getCartById(cartId);
        var product = productDTO.toEntity(productDTO);
        cart.getProducts().add(product);
        cartService.updateCart(cart);
    }

    @PatchMapping(value = "/remove")
    public void RemoveFromCart(@RequestParam Long cartId, @RequestBody ProductDTO productDTO){
        var cart = cartService.getCartById(cartId);
        var product = productDTO.toEntity(productDTO);
        cart.getProducts().remove(product);
        cartService.updateCart(cart);
    }
}
