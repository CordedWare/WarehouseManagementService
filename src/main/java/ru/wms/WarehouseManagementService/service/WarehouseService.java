package ru.wms.WarehouseManagementService.service;


import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.WarehouseRepo;

import java.util.List;

@Service
public class WarehouseService {
    private WarehouseRepo warehouseRepo;

    public List<Warehouse> getAllWarehouses() {

        return warehouseRepo.findAll();
    }
}
