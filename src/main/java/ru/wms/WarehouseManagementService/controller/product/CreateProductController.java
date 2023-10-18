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

@Controller
@RequestMapping("/createProduct")
public class CreateProductController {


    private final ProductService productService;

    private WarehouseService warehouseService;


    public CreateProductController(ProductService productService, WarehouseService warehouseService) {
        this.productService = productService;
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public String createProducts(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        var user = userPrincipal.getUser();

        Iterable<Product> productList = productService.getAllProducts();
        Iterable<Warehouse> warehouseList = warehouseService.getAllWarehouses(user);
        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("products", productList);
        model.addAttribute("product", new Product());

        return "createProduct";
    }

    @PostMapping
    public Product createProduct(
            @ModelAttribute("product")
            @Valid Product product,
            @Valid Warehouse warehouse,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid product data");
        }
        var user = userPrincipal.getUser();

        product.setUser(user);
        product.setWarehouse(warehouse);

        var savedProduct = productService.saveProduct(product);

        return savedProduct;
    }
}
