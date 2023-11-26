package ru.wms.WarehouseManagementService.controller.warehouse;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.WarehouseService;

@Controller
@RequestMapping("/createWarehouse")
public class CreateWarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String CreateWarehouses(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Iterable<Warehouse> warehousesList = warehouseService.getAllWarehouses(userPrincipal.getUser());

        model.addAttribute("warehouses", warehousesList);
        model.addAttribute("warehouse", new Warehouse());

        return "/warehouse/createWarehouse";
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
