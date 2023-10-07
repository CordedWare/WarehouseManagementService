package ru.wms.WarehouseManagementService.controller.order;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Order;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.OrderService;


@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String orders(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("orders", orderService.getAllOrders());
        model.addAttribute("productss",productRepository.findAll());
        return "orders";
    }

    @PostMapping("/createOrder")
    public String createOrder( @ModelAttribute("order")
                              @Valid Order order,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid order data");
        }
        order.setUser(userPrincipal.getUser());
        orderService.saveNewOrder(order);
        return "redirect:/orders";
    }


    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.findOrder(id);
    }
}
