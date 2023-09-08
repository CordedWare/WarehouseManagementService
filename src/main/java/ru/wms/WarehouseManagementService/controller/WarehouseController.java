package ru.wms.WarehouseManagementService.controller;

import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private WarehouseService warehouseService;

    @GetMapping
    public List<Warehouse> getAllWarehouses() {

        return warehouseService.getAllWarehouses();
    }

    @PostMapping
    public Warehouse createWarehouseById(@RequestBody Warehouse warehouse) {

        return createWarehouseById(warehouse);
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable Long id) {

        return getWarehouseById(id);
    }
}