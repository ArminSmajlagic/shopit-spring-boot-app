package com.shopit.shop.Controller;

import com.shopit.shop.DTO.ShopDTO;
import com.shopit.shop.Exception.NotFoundException;
import com.shopit.shop.Service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;
    @GetMapping
    public ResponseEntity<List<ShopDTO>> getAll(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) throws NotFoundException{
        ArrayList<ShopDTO> shops = new ArrayList<>(size);

        for (var shop: shopService.getShops(page, size, sortBy, direction)){
            shops.add(ShopDTO.fromEntity(shop));
        }

        if(shops.isEmpty())
            throw new NotFoundException("No products found");

        return ResponseEntity.ok().body(shops);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getById(@PathVariable Long id) throws NotFoundException{
        var shop = shopService.getShopById(id);
        if(shop == null)
            throw new NotFoundException("No shop found for this id");
        return ResponseEntity.ok(ShopDTO.fromEntity(shop));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShopDTO create(@RequestBody ShopDTO shopDTO){
        return ShopDTO.fromEntity(shopService.saveShop(ShopDTO.toEntity(shopDTO)));
    }

    @PutMapping("/{id}")
    public ShopDTO update(@PathVariable Long id, @RequestBody ShopDTO shopDTO){
        return ShopDTO.fromEntity(shopService.updateShop(ShopDTO.toEntity(shopDTO)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        shopService.deleteShop(id);
    }
}
