package ru.wms.WarehouseManagementService.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.ProductService;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private final ProductService productService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    public ProductController(ProductService productService, UserRepository userRepository, ProductRepository productRepository, WarehouseService warehouseService) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.warehouseService = warehouseService;
    }

    @GetMapping
    public String products(Model model, @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        var user = userPrincipal.getUser();

        Iterable<Product> productList = productService.getAllProducts();
        Iterable<Warehouse> warehouseList = warehouseService.getAllWarehouses(user);
        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("products", productList);
        model.addAttribute("product", new Product());
        return "products";
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

        Product savedProduct = productService.saveProduct(product);

        return savedProduct;
    }

    @GetMapping("/{id}")
    public String getProductsByName(@RequestParam(name = "filter", required = false, defaultValue = "") String filter, Model model) {
        Iterable<Product> productList = productRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            productList = productRepository.findByNameContaining(filter);
        } else {
            productList = productRepository.findAll();
        }
        model.addAttribute("products", productList);
        model.addAttribute("filter", filter);
        model.addAttribute("product", new Product());

        return "products";
    }

    @PostMapping("/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);

        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> productOptional = Optional.ofNullable(productService.getProductById(id));

        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());

            return "edit-product";
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    @PostMapping("/{id}/edit")
    public String saveEditedProduct(@PathVariable("id") Long id, @ModelAttribute("product") Product product) {
        productService.updateProduct(id, product);

        return "redirect:/products";
    }
}