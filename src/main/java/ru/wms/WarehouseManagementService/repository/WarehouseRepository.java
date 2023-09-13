package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;

import java.util.List;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {

    List<Warehouse> findAllByUser(User user);

    List<Warehouse> findByName(String name);

    List<Warehouse> findByNameContaining(String name);
}