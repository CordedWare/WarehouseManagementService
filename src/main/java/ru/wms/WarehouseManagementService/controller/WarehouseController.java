package ru.wms.WarehouseManagementService.controller;

import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    private WarehouseService warehouseService;


    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {

        return createWarehouse(warehouse);
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable Long id) {

        return getWarehouseById(id);
    }

}
