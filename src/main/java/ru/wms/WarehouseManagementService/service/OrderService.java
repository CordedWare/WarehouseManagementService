package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.OrderDTO;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.Order;
import ru.wms.WarehouseManagementService.entity.OrderStatus;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    public List<Order> getAllOrders() {

        return orderRepo.findAll();
    }

    public Order saveNewOrder(Order order) {

        order.setQuantity(order.getProducts().size());
        order.setDelivery(false);
        order.setStatus(OrderStatus.NEW);

        var totalCost = BigDecimal.ZERO;
        for(var p : order.getProducts())
        {
            var price = p.getPrice();
            var q = p.getQuantity();
            var quant = new BigDecimal(q);
            var x = price.multiply(quant);
            totalCost = totalCost.add(x);
        }

        order.setTotalCost(totalCost);

        return orderRepo.save(order);
    }

    public Order findOrder(Long id){

        return orderRepo.findOrderById(id);
    }

    public List<Order> getAllMyOrders(User user) {
        if(user instanceof Employee employee)
            return orderRepo.findAllByOwner(employee.getCustomer());

        return orderRepo.findAllByOwner(user);
    }

    public void changeStatus(OrderDTO orderDTO, OrderStatus orderStatus) {
        var order = orderRepo.findOrderById(orderDTO.getId());
        order.setStatus(orderStatus);
        orderRepo.save(order);
    }
}