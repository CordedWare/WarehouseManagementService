package ru.wms.WarehouseManagementService.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.WarehouseService;


@Controller
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String warehouses(Model model,@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Iterable<Warehouse> warehousesList = warehouseService.getAllWarehouses(userPrincipal.getUser());
        model.addAttribute("warehouses", warehousesList);
        model.addAttribute("warehouse", new Warehouse());

        return "warehouses";
    }

    @PostMapping
    public String createWarehouse(
            @ModelAttribute("warehouse")
            @Valid Warehouse warehouse,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid warehouse data");
        }

        var user = userPrincipal.getUser();
        warehouseService.saveWarehouse(warehouse, user);

        return "redirect:/warehouses";
    }
}
