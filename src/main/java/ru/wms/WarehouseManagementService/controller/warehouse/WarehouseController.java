package ru.wms.WarehouseManagementService.controller.warehouse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    @Autowired
    private final WarehouseService warehouseService;

    @GetMapping("/create")
    public String createPage(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        model.addAttribute("warehouse", new Warehouse());
        return "warehouse/create";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute("warehouse")
            @Valid Warehouse warehouse,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid warehouse data");
        }
        var user = userPrincipal.getUser();
        warehouseService.saveWarehouse(warehouse, user.getCompany());
        return "redirect:/warehouses";
    }

    @GetMapping()
    public String warehousesSort(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "sortPath", defaultValue = "asc") String sortPath,
            Model model
    ) {

        Optional<Iterable<Warehouse>> warehousesList;
        warehousesList = warehouseService.sorted(sortBy, sortPath);

        model.addAttribute("warehouses", warehousesList);
        model.addAttribute("warehouse", new Warehouse());

        return "warehouse/warehouses";
    }

    @GetMapping("/{warehouseId}/products")
    public String warehouseProducts(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model,
            @PathVariable("warehouseId") Long warehouseId
    ) {
        var warehouse = warehouseService.getById(warehouseId);
        model.addAttribute("products", warehouse.get().getProductList());
        model.addAttribute("product", new Product());
        model.addAttribute("warehouseId", warehouseId);

        return "product/products";
    }


    @GetMapping("/search")
    public String getWarehousesByName(
            @RequestParam(
                    name = "name",
                    required = false,
                    defaultValue = "")
            Optional<String> nameFilterOpt,
            Model model) {
        Optional<Iterable<Warehouse>> warehouseList = Optional.of(nameFilterOpt
                .filter(filter -> !filter.isEmpty())
                .flatMap(name -> warehouseService.findByNameContaining(name.trim()))
                .orElse(new ArrayList<>()));

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("filter", nameFilterOpt);
        model.addAttribute("warehouse", new Warehouse());

        return "warehouse/warehouses";
    }

    @PostMapping("/delete/{id}")
    public String deleteWarehouse(@PathVariable Long id) {
        warehouseService.deleteWarehouseById(id);
        return "redirect:/warehouses";
    }
}
