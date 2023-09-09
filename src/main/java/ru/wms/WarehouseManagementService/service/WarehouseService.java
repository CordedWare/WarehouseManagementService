package ru.wms.WarehouseManagementService.service;


import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;

import java.util.List;

@Service
public class WarehouseService {
    private WarehouseRepository warehouseRepo;

    public List<Warehouse> getAllWarehouses() {

        return warehouseRepo.findAll();
    }
}
