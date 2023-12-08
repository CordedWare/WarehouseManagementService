package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Order;
import ru.wms.WarehouseManagementService.entity.OrderStatus;
import ru.wms.WarehouseManagementService.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderById(Long id);

    List<Order> findAllByOwnerAndStatus(User user, OrderStatus status);

}
