package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.OrderDTO;
import ru.wms.WarehouseManagementService.dto.OrderMoveDTO;
import ru.wms.WarehouseManagementService.entity.*;
import ru.wms.WarehouseManagementService.repository.OrderRepository;

import java.math.BigDecimal;
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
        order.setDelivery(false);
        order.setStatus(OrderStatus.NEW);

        var totalCost = order
                .getProducts()
                .stream()
                .map( product ->
                        product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity()))
                ).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalCost(totalCost);

        order.getProducts().forEach( product ->
                product.setOrderSet(Collections.singleton(order)));

        return orderRepo.save(order);
    }

    public Order findOrder(Long id){

        return orderRepo.findOrderById(id);
    }

    public List<Order> getAllMyOrders(User user, OrderStatus status) {
        if (user instanceof Employee employee)

            return orderRepo.findAllByOwnerAndStatus(employee.getCustomer(), status);

        return orderRepo.findAllByOwnerAndStatus(user,status);
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

        productService.move(products, warehouse);
    }

}
