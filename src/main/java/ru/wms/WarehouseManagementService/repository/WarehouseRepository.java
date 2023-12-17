package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Company;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;

import java.util.List;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, Long> {

    Iterable<Warehouse> findAllByCompany(Company company);
    Iterable<Warehouse> findAllById(Long id);

    Iterable<Warehouse> findAll();

    Iterable<Warehouse> findByNameContaining(String name);


    @Query(value = "SELECT * FROM warehouse_t ORDER BY creation_date",
            nativeQuery = true)
    List<Warehouse> sortedByDate();
}
