package ru.wms.WarehouseManagementService.controller.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.Optional;


@Controller
@RequestMapping("/warehouses")
public class MainWarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping
    public String warehouses(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Iterable<Warehouse> warehousesList = warehouseService.getAllWarehouses(userPrincipal.getUser());
        model.addAttribute("warehouses", warehousesList);
        model.addAttribute("warehouse", new Warehouse());

        return "/warehouse/warehouses";
    }

    @GetMapping("/{id}")
    public String getWarehousesByName(@RequestParam(name = "filter", required = false, defaultValue = "") Optional<String> nameFilter, Model model) {
        Iterable<Warehouse> warehouseList = warehouseRepository.findAll();
        if (nameFilter.isPresent() && !nameFilter.isEmpty()) {
            warehouseList = warehouseRepository.findByNameContaining(nameFilter.get());
        } else {
            warehouseList = warehouseRepository.findAll();
        }

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("filter", nameFilter);
        model.addAttribute("warehouse", new Warehouse());

        return "/warehouse/warehouses";
    }

    @PostMapping("/{id}")
    public String deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouseById(id);

        return "redirect:/warehouses";
    }
}
