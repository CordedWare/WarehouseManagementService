package ru.wms.WarehouseManagementService.controller.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.ArrayList;
import java.util.Optional;


@Controller
@RequestMapping("/warehouses")
public class MainWarehouseController {

    /**
     * Основной контроллер склада
     */

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String warehouses(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        Optional<Iterable<Warehouse>> warehousesList = warehouseService.getAllWarehouses(userPrincipal.getUser());
        model.addAttribute("warehouses", warehousesList);
        model.addAttribute("warehouse", new Warehouse());

        return "warehouse/warehouses";
    }

    @GetMapping("/{id}")
    public String getWarehousesByName(
            @RequestParam(
                    name = "filter",
                    required = false,
                    defaultValue = "")
            Optional<String> nameFilterOpt,
            Model model
    ) {
        Optional<Iterable<Warehouse>> warehouseList = Optional.of(nameFilterOpt
                .filter( filter -> !filter.isEmpty())
                .flatMap(  name -> warehouseService.findByNameContaining(name))
                .orElseGet(  () -> warehouseService.getAllWarehouses().orElse(new ArrayList<>())));

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("filter",     nameFilterOpt);
        model.addAttribute("warehouse",  new Warehouse());

        return "warehouse/warehouses";
    }

    @PostMapping("/{id}")
    public String deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouseById(id);

        return "redirect:/warehouses";
    }

}
