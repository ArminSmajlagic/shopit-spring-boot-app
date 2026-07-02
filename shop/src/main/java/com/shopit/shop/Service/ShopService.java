package com.shopit.shop.Service;

import com.shopit.shop.Entity.Shop;
import com.shopit.shop.Repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopService {
    private final ShopRepository shopRepository;

    public Page<Shop> getShops(
            int page,
            int size,
            String sortBy,
            String direction
    ){
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return shopRepository.findAll(pageable);
    }

    public Shop getShopById(Long id){
        return shopRepository.findById(id).orElse(null);
    }

    public Shop saveShop(Shop shop){
        return shopRepository.save(shop);
    }

    public Shop updateShop(Shop shop){
        Shop existing = getShopById(shop.getId());

        existing.setName(shop.getName());
        existing.setDescription(shop.getDescription());
        existing.setAddress(shop.getAddress());
        existing.setPhone(shop.getPhone());

        return shopRepository.save(existing);
    }

    public void deleteShop(Long id){
        shopRepository.deleteById(id);
    }
}
