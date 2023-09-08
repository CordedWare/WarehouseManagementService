package ru.wms.WarehouseManagementService.service;

import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Order;
import ru.wms.WarehouseManagementService.repo.OrderRepo;

import java.util.List;

@Service
public class OrderService {
    private OrderRepo orderRepo;

    public List<Order> getAllOrders() {

        return orderRepo.findAll();
    }
}