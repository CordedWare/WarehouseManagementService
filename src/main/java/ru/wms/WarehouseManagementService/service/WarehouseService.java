package ru.wms.WarehouseManagementService.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Iterable<Warehouse> getAllMyWarehouses(User user) {
        if(user instanceof Employee emp){
            return warehouseRepository.findAllByOwner(emp.getCustomer());
        }
        return warehouseRepository.findAllByOwner(user);
    }

    public Warehouse saveWarehouse(Warehouse warehouse, User user) {
        warehouse.setOwner(user);
        return warehouseRepository.save(warehouse);
    }

    public void deleteWarehouseById(Long id) {
        warehouseRepository.deleteById(id);
    }

    public Warehouse getWarehouse(Long warehouseId) {
        return warehouseRepository.findById(warehouseId).get();
    }
}
