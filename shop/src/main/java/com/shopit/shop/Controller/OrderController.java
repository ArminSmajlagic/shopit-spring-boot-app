package com.shopit.shop.Controller;

import com.shopit.shop.DTO.OrderDTO;
import com.shopit.shop.Exception.NotFoundException;
import com.shopit.shop.Service.OrderService;
import com.shopit.shop.Service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAll(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction
    ) throws NotFoundException {

        ArrayList<OrderDTO> result = new ArrayList<>(size);

        for (var order: orderService.getOrders(page, size, sortBy, direction)){
            result.add(OrderDTO.toDTO(order));
        }

        if(result.isEmpty())
            throw new NotFoundException("No orders found");

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(
            @RequestParam Long id
    ) throws NotFoundException {
        var order = orderService.getOrderById(id);
        if(order == null)
            throw new NotFoundException("No order found for this id");
        return ResponseEntity.ok(OrderDTO.toDTO(order));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO create(@RequestBody OrderDTO orderDTO){
        return OrderDTO.toDTO(orderService.saveOrder(orderDTO.toEntity(orderDTO)));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam Long id){
        orderService.deleteOrder(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody OrderDTO orderDTO){
        var existing = orderService.getOrderById(id);
        existing.setPaymentMethod(orderDTO.paymentMethod());
        existing.setShippingStatus(orderDTO.shippingStatus());
        existing.setStatus(orderDTO.status());
        existing.setPaymentStatus(orderDTO.paymentStatus());
        orderService.saveOrder(existing);
    }
}
