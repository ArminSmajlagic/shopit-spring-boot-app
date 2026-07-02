package com.shopit.shop.Service;

import com.shopit.shop.Entity.Cart;
import com.shopit.shop.Repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public Page<Cart> getCarts(
            int page,
            int size,
            String sortBy,
            String direction
    ){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return cartRepository.findAll(pageable);
    }

    public Cart getCartById(Long id){
        return cartRepository.findById(id).orElse(null);
    }

    public Cart saveCart(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart){
        Cart existing = getCartById(cart.getId());

        existing.setProducts(cart.getProducts());

        return cartRepository.save(existing);
    }

    public void deleteCart(Long id){
        cartRepository.deleteById(id);
    }
}
