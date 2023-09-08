package ru.wms.WarehouseManagementService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
}