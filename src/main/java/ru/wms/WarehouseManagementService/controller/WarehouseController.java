package ru.wms.WarehouseManagementService.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.repository.WarehouseRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.List;

@Controller
@RequestMapping("/warehouses")
public class WarehouseController {

    private final WarehouseService warehouseService;
    private final UserRepository userRepository;
    private final WarehouseRepository warehouseRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WarehouseController(WarehouseService warehouseService, UserRepository userRepository, WarehouseRepository warehouseRepository, ProductRepository productRepository) {
        this.warehouseService = warehouseService;
        this.userRepository = userRepository;
        this.warehouseRepository = warehouseRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String warehouses(Model model) {
        Iterable<Warehouse> warehousesList = warehouseService.getAllWarehouses();
        model.addAttribute("warehouses", warehousesList);
        model.addAttribute("warehouse", new Warehouse());

        return "warehouses";
    }

    @PostMapping
    public Warehouse createWarehouse(
            @ModelAttribute("warehouse")
            @Valid Warehouse warehouse,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid warehouse data");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

//        Product product = productRepository.findById(userPrincipal.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        warehouse.setUser(user);
//        warehouse.setProduct(product);

        Warehouse savedWarehouse = warehouseService.saveWarehouse(warehouse);

        return savedWarehouse;
    }
}
