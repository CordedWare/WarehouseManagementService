package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.*;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;

import java.util.Optional;

@Service
public class WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    public Optional<Iterable<Warehouse>> getAllWarehouses(Company company) {

        return Optional.ofNullable(warehouseRepository.findAllByCompany(company));
    }

    public Warehouse saveWarehouse(Warehouse warehouse, Company company) {
        warehouse.setCompany(company);

        return Optional.of(warehouseRepository.save(warehouse))
                .orElseThrow(() ->
                        new RuntimeException("Ошибка: склад не был сохранен "));
    }

    public Warehouse getWarehouseById(Long warehouseId) {

        return Optional.of(warehouseRepository.findById(warehouseId).get())
                .orElseThrow(() ->
                        new RuntimeException("Ошибка: склад не найден "));
    }

    public Optional<Warehouse> getById(Long id) {

        return Optional.of(warehouseRepository.findById(id)
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
