package ru.wms.WarehouseManagementService.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.ProductService;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class MainProductController {

    /**
     * Основной контроллер товара
     */

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseService warehouseService;

    @GetMapping
    public String products(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        var user = userPrincipal.getUser();
        Iterable<Product> productList = productService.getAllMyProducts(user);
        Iterable<Warehouse> warehouseList = warehouseService.getAllMyWarehouses(user);

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("products", productList);
        model.addAttribute("product", new Product());

        return "/product/products";
    }

    @GetMapping("/{id}")
    public String getProductsByName(
            @RequestParam(
                    name = "filter",
                    required = false,
                    defaultValue = "")
            Optional<String> filter, Model model
    ) {
        Iterable<Product> productList;
        if (filter.isPresent() && !filter.isEmpty()) {
            productList = productRepository.findByNameContaining(filter.get());
        } else {
            productList = productRepository.findAll();
        }
        model.addAttribute("products", productList);
        model.addAttribute("filter", filter);
        model.addAttribute("product", new Product());

        return "products";
    }

    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);

        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showEditProductForm(
            @PathVariable("id") Long id,
            Model model
    ) {
        Optional<Product> productOptional = Optional.ofNullable(productService.getProductById(id)); //TODO порефакторить с Optional более лаконично

        if (productOptional.isPresent()) {
            model.addAttribute("product", productOptional.get());

            return "edit-product";
        } else {
            throw new RuntimeException("Товар не найден по id: " + id);
        }
    }

    @PostMapping("/{id}/edit")
    public String saveEditedProduct(
            @PathVariable("id") Long id,
            @ModelAttribute("product")
            Product product
    ) {
        productService.updateProduct(id, product);

        return "redirect:/products";
    }

}
