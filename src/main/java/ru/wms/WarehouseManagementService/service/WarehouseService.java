package ru.wms.WarehouseManagementService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Iterable<Warehouse> getAllWarehouses(User user) {

        return warehouseRepository.findAllByUser(user);
    }

    public Warehouse saveWarehouse(Warehouse warehouse, User user) {
        warehouse.setUser(user);

        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouseById(Long id) {
        warehouseRepository.deleteById(id);
    }
}
