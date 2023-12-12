package ru.wms.WarehouseManagementService.controller.product;

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
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.ProductService;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.Optional;

@Controller
@RequestMapping("/createProduct")
public class CreateProductController {

    /**
     * Контроллер создания товара
     */

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String createProducts(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        var user = userPrincipal.getUser();
        Optional<Iterable<Product>> productList = productService.getAllMyProducts(user);
        Optional<Iterable<Warehouse>> warehouseList = warehouseService.getAllWarehouses(user);

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("products", productList);
        model.addAttribute("product", new Product());

        return "/product/createProduct";
    }

    @PostMapping
    public String createProduct(
            @ModelAttribute("product")
            @Valid Product product,
            @Valid Long warehouse,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Неверные данные о товаре");
        }

        productService.createProduct(
                product,
                warehouse,
                userPrincipal.getUser()
        );

        return "redirect:/products";
    }

}
