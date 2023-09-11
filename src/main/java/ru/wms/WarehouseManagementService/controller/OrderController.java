package ru.wms.WarehouseManagementService.controller;

import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Order;
import ru.wms.WarehouseManagementService.service.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return getOrderById(id);
    }
}
