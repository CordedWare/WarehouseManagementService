package ru.wms.WarehouseManagementService.controller.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.dto.OrderDTO;
import ru.wms.WarehouseManagementService.dto.OrderMoveDTO;
import ru.wms.WarehouseManagementService.entity.Order;
import ru.wms.WarehouseManagementService.entity.OrderStatus;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.OrderService;
import ru.wms.WarehouseManagementService.service.ProductService;
import ru.wms.WarehouseManagementService.service.WarehouseService;


@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    /**
     * Основной контроллер заказа
     */

    private final OrderService orderService;

    private final ProductService productService;

    private final WarehouseService warehouseService;

    @GetMapping
    public String orders(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model,
            @RequestParam(
                    name = "status",
                    required = false,
                    defaultValue = "NEW")
            OrderStatus status
    ) {
        model.addAttribute("orders", orderService.getOrders(userPrincipal.getUser().getCompany(), status));
        model.addAttribute("orderDTO", new OrderDTO());

        return "order/orders";
    }

    @GetMapping("/manage")
    public String manageOrders(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        model.addAttribute("orders", orderService.getOrders(userPrincipal.getUser().getCompany(), OrderStatus.PROCESSING));
        model.addAttribute("productList", productService.getCompanyProducts(userPrincipal.getUser().getCompany()).get());
        model.addAttribute("order", new Order());

        return "order/manage";
    }

    @GetMapping("/manage/move/{id}")
    public String moveOrdersProduct(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model,
            @PathVariable(name = "id")
            Long orderId
    ) {
//        model.addAttribute("orders", orderService.getAllMyOrders(userPrincipal.getUser(),OrderStatus.PROCESSING));
        model.addAttribute("order", orderService.getById(orderId));
        model.addAttribute("orderMoveDTO", new OrderMoveDTO());
        model.addAttribute("warehouses", warehouseService.getCompanyWarehouse(userPrincipal.getUser().getCompany()).get());

        return "order/move";
    }


    @PostMapping("/manage/move")
    public String moveOrdersProduct(
            OrderMoveDTO orderMoveDTO,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        orderService.moveToOtherWarehouse(orderMoveDTO);
        return "redirect:/orders";
    }

    @PostMapping("/manage/create")
    public String createOrder(
            @ModelAttribute("order")
            @Valid Order order,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Неверные данные заказа");
        }
        order.setCompany(userPrincipal.getUser().getCompany());
        orderService.saveNewOrder(order);

        return "redirect:/orders";
    }

    @PostMapping("/status/process")
    public String processOrder(
            @ModelAttribute("order")
            @Valid OrderDTO orderDTO
    ) {
        orderService.changeStatus(orderDTO, OrderStatus.PROCESSING);

        return "redirect:/orders";
    }

    @PostMapping("/status/competed")
    public String competedOrder(
            @ModelAttribute("order")
            @Valid OrderDTO orderDTO
    ) {
        orderService.changeStatus(orderDTO, OrderStatus.COMPLETED);

        return "redirect:/orders";
    }


    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {

        return orderService.findOrder(id);
    }

}
