package ru.wms.WarehouseManagementService.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.ProductService;
import org.springframework.security.core.Authentication;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Autowired
    public ProductController(ProductService productService, UserRepository userRepository, ProductRepository productRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String products(Model model) {
        Iterable<Product> productList = productService.getAllProducts();
        model.addAttribute("products", productList);
        model.addAttribute("product", new Product());
        return "products";
    }

    @PostMapping
    public Product createProduct(
            @ModelAttribute("product")
            @Valid Product product,
            BindingResult bindingResult,
            Authentication authentication
    ) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException("Invalid product data");
        }
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(userPrincipal.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        product.setUser(user);

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