package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Company;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {

    Iterable<Warehouse> findAllByCompany(Company company);

    Iterable<Warehouse> findAll();

    Iterable<Warehouse> findByNameContaining(String name);

}
