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

    public Iterable<Warehouse> getAllMyWarehouses(User user) {
        if (user instanceof Employee emp)

            return warehouseRepository.findAllByOwner(emp.getCustomer());


        return warehouseRepository.findAllByOwner(user);
    }
//    public Optional<Warehouse> getAllMyWarehousesByUser(User user) { //TODO порефакторить с Optional
//        return (user instanceof Employee) ?
//                warehouseRepository.findAllByOwner(((Employee) user).getCustomer()) :
//                warehouseRepository.findAllByOwner(user);
//    }

    public Iterable<Warehouse> getAllMyWarehouses() {

        return warehouseRepository.findAll();
    }

//    public Optional<List<Warehouse>> findByNameContaining(String name) { //TODO порефакторить с Optional
//
//        return warehouseRepository.findByNameContaining(name);
//    }


    public Optional<Warehouse> saveWarehouse(Warehouse warehouse, User user) {
        warehouse.setOwner(user);

//        return Optional.of(warehouseRepository.save(warehouse));
        return Optional
                .ofNullable(Optional.of(warehouseRepository.save(warehouse))
                        .orElseThrow( () ->
                                new RuntimeException("Ошибка: склад не был сохранен ")));
    }

    public void deleteWarehouseById(Long id) {
        warehouseRepository.deleteById(id);
    }

    public Warehouse getWarehouseById(Long warehouseId) {

        return warehouseRepository.findById(warehouseId).get();
//        return Optional //TODO порефакторить с Optional
//                .ofNullable(Optional.of(warehouseRepository.findById(warehouseId).get())
//                        .orElseThrow( () ->
//                                new RuntimeException("Ошибка: склад не найден ")));
    }

    public Warehouse getById(Long id){
        var optWarehouse = warehouseRepository.findById(id);

        if(optWarehouse.isEmpty())
            throw new IllegalArgumentException();

        return optWarehouse.get();
    }

}
