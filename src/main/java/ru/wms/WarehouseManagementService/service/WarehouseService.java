package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.*;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseRepository warehouseRepository;

    public Optional<Iterable<Warehouse>> sorted(String sortBy, String sortPath) {
        Iterable<Warehouse> result;

        switch (sortBy) {
            case "name":
                result = warehouseRepository.sortedByName();
                break;
            case "address":
                result = warehouseRepository.sortedByAddress();
                break;
            case "capacity":
                result = warehouseRepository.sortedByCapacity();
                break;
            case "creationDate":
                result = warehouseRepository.sortedByDate();
                break;
            default:
                return Optional.empty();
        }

        List<Warehouse> sortedList = new ArrayList<>();
        result.forEach(i -> sortedList.add(i));

        if ("desc".equalsIgnoreCase(sortPath)) {
            Collections.reverse(sortedList);
        }

       result = sortedList;

        return Optional.of(result);
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

    public Optional<Iterable<Warehouse>> getCompanyWarehouse(Company company) {
        var warehouseList = warehouseRepository.findAllByCompany(company);
        return Optional.ofNullable(warehouseList);
    }

    public Optional<Iterable<Warehouse>> findByNameContaining(String name) {

        return Optional.ofNullable(warehouseRepository.findByNameContaining(name));
    }

    public void deleteWarehouseById(Long id) {
        warehouseRepository.deleteById(id);
    }

    public Optional<Iterable<Warehouse>> findAllById(Long warehouseId) {
        return Optional.ofNullable(warehouseRepository.findAllById(warehouseId));
    }

}
