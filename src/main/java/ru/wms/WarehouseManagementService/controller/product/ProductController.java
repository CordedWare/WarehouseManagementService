package ru.wms.WarehouseManagementService.controller.product;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.ProductService;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    /**
     * Основной контроллер товара
     */

    @Autowired
    private ProductService productService;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping("/add")
    public String add(@RequestParam(required = true) Long warehouseId,
                      Model model,
                      @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {

        var user = userPrincipal.getUser().getCompany();
        Optional<Iterable<Warehouse>> warehouseList = warehouseService.getAllWarehouses(user);

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("product", new Product());
        return "product/add";
    }


    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @ModelAttribute("product")
            @Valid Product product,
            @Valid Long warehouse,
            BindingResult bindingResult
    ) {
        Optional.of(bindingResult)
                .filter(BindingResult::hasErrors)
                .ifPresent(errors -> {
                    throw new IllegalArgumentException("Неверные данные о товаре");
                });

        productService.createProduct(
                product,
                warehouse,
                userPrincipal.getUser()
        );

        return "redirect:/warehouses";
    }


    @GetMapping("/{id}")
    public String getProductsByName(
            @RequestParam(
                    name = "filter",
                    required = false,
                    defaultValue = "")
            Optional<String> nameFilterOpt,
            Model model
    ) {
        Optional<Iterable<Product>> productList = Optional.of(
                nameFilterOpt
                        .filter(filter -> !filter.isEmpty())
                        .flatMap(name -> productService.findByNameContaining(name))
                        .orElseGet(() -> productService.getAllProducts().orElse(new ArrayList<>()))
        );

        model.addAttribute("products", productList);
        model.addAttribute("filter", nameFilterOpt);
        model.addAttribute("product", new Product());

        return "product/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, HttpServletRequest request) {
        productService.deleteProductById(id);

        var referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/{id}/edit")
    public String saveEditedProduct(
            @PathVariable Long id,
            @ModelAttribute Product editedProduct,
            HttpServletRequest request
    ) {
        productService.updateProduct(id, editedProduct);
        var referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

}
