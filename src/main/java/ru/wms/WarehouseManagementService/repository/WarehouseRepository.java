package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Warehouse;


@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {

}