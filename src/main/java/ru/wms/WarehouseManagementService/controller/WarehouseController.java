package ru.wms.WarehouseManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.List;

@RestController
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {

        return null;
    }

    @GetMapping("/{id}")
    public Warehouse getWarehouseById(@PathVariable Long id) {

        return null;
    }

}
