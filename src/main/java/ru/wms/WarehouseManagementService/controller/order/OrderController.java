package ru.wms.WarehouseManagementService.controller.order;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.dto.OrderDTO;
import ru.wms.WarehouseManagementService.entity.Order;
import ru.wms.WarehouseManagementService.entity.OrderStatus;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.OrderService;
import ru.wms.WarehouseManagementService.service.ProductService;


@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public String orders(@AuthenticationPrincipal UserPrincipal userPrincipal, Model model,@RequestParam(name = "status",required = false,defaultValue = "NEW") OrderStatus status) {
        model.addAttribute("order", new Order());
        model.addAttribute("orders", orderService.getAllMyOrders(userPrincipal.getUser(),status));
        model.addAttribute("productss", productService.getAllMyProducts(userPrincipal.getUser()));
        model.addAttribute("orderDTO", new OrderDTO());

        return "/order/orders";
    }

    @PostMapping("/createOrder")
    public String createOrder(@ModelAttribute("order")
                              @Valid Order order,
                              BindingResult bindingResult,
                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid order data");
        }
        order.setOwner(userPrincipal.getUser());
        orderService.saveNewOrder(order);
        return "redirect:/orders";
    }

    @PostMapping("/process")
    public String processOrder(@ModelAttribute("order")
                              @Valid OrderDTO orderDTO) {
        orderService.changeStatus(orderDTO, OrderStatus.PROCESSING);
        return "redirect:/orders";
    }


    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {

        return orderService.findOrder(id);
    }
}
