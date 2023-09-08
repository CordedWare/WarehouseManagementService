package ru.wms.WarehouseManagementService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Warehouse;

@Repository
public interface WarehouseRepo extends JpaRepository<Warehouse, Long> {
}