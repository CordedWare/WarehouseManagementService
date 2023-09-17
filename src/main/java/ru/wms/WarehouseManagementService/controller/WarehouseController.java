package ru.wms.WarehouseManagementService.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.WarehouseService;


@Controller
@RequestMapping("/warehouses")
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping
    public String warehouses(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
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

    @GetMapping("/{id}")
    public String getWarehousesByName(@RequestParam(name = "filter", required = false, defaultValue = "") String nameFilter, Model model) {
        Iterable<Warehouse> warehouseList = warehouseRepository.findAll();
        if (nameFilter != null && !nameFilter.isEmpty()) {
            warehouseList = warehouseRepository.findByNameContaining(nameFilter);
        } else {
            warehouseList = warehouseRepository.findAll();
        }

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("filter", nameFilter);
        model.addAttribute("warehouse", new Warehouse());

        return "warehouses";
    }

    @PostMapping("/{id}")
    public String deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouseById(id);
        return "redirect:/warehouses";
    }
}
