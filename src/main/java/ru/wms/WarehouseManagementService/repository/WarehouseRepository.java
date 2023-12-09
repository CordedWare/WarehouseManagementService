package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {

    Iterable<Warehouse> findAllByOwner(User user);

    Iterable<Warehouse> findAll();

    Optional<List<Warehouse>> findByName(String name);

    Iterable<Warehouse> findByNameContaining(String name);

    Optional<List<Warehouse>> findByAddressContaining(String address);

}
