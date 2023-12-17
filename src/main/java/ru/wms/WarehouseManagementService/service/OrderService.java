package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.OrderDTO;
import ru.wms.WarehouseManagementService.dto.OrderMoveDTO;
import ru.wms.WarehouseManagementService.entity.*;
import ru.wms.WarehouseManagementService.repository.OrderRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders() {

        return orderRepo.findAll();
    }

    public Order saveNewOrder(Order order) {

        order.setQuantity(order.getProducts().size());
        order.setStatus(OrderStatus.NEW);

        order.getProducts().forEach(product ->
                product.setOrderSet(Collections.singleton(order)));

        return Optional.of(orderRepo.save(order))
                .orElseThrow( () ->
                        new RuntimeException("Ошибка при сохранении заказа"));
    }

    public Order findOrder(Long id){

        return orderRepo.findOrderById(id);
    }

    public List<Order> getOrders(Company company, OrderStatus status) {
        return orderRepo.findAllByCompanyAndStatus(company, status);
    }

    public void changeStatus(OrderDTO orderDTO, OrderStatus orderStatus) {
        var order = orderRepo.findOrderById(orderDTO.getId());
        order.setStatus(orderStatus);

        orderRepo.save(order);
    }

    public Order getById(Long orderId) {

        return orderRepo.findOrderById(orderId);
    }

    public void moveToOtherWarehouse(OrderMoveDTO orderMoveDTO) {
        var order     = orderRepo.findOrderById(orderMoveDTO.getOrderId());
        var warehouse = warehouseService.getWarehouseById(orderMoveDTO.getWarehouseId());
        var products  = order.getProducts();

        productService.move(products, Optional.ofNullable(warehouse));
    }

}
