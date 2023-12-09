package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Optional<Iterable<Warehouse>> getAllWarehouses(User user) {

        return Optional.ofNullable((user instanceof Employee) ?
                warehouseRepository.findAllByOwner(((Employee) user).getCustomer()) :
                warehouseRepository.findAllByOwner(user));
    }

    public Optional<Warehouse> saveWarehouse(Warehouse warehouse, User user) {
        warehouse.setOwner(user);

        return Optional
                .ofNullable(Optional.of(warehouseRepository.save(warehouse))
                        .orElseThrow( () ->
                                new RuntimeException("Ошибка: склад не был сохранен ")));
    }

    public Optional<Warehouse> getWarehouseById(Long warehouseId) {

        return Optional
                .ofNullable(Optional.of(warehouseRepository.findById(warehouseId).get())
                        .orElseThrow( () ->
                                new RuntimeException("Ошибка: склад не найден ")));
    }

    public Optional<Warehouse> getById(Long id) {

        return Optional
                .ofNullable(warehouseRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Ошибка: Склад не найден по id :" + id)));
    }

    public Optional<Iterable<Warehouse>> getAllWarehouses() {

        return Optional.ofNullable(warehouseRepository.findAll());
    }

    public Optional<Iterable<Warehouse>> findByNameContaining(String name) {

        return Optional.ofNullable(warehouseRepository.findByNameContaining(name));
    }

    public void deleteWarehouseById(Long id) {
        warehouseRepository.deleteById(id);
    }

}
