package ru.wms.WarehouseManagementService.controller.product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.Warehouse;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.ProductService;
import ru.wms.WarehouseManagementService.service.WarehouseService;

import java.util.ArrayList;
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
    private WarehouseService warehouseService;

    @GetMapping
    public String products(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        var user = userPrincipal.getUser();
        Optional<Iterable<Product>> productList = productService.getAllMyProducts(user);
        Optional<Iterable<Warehouse>> warehouseList = warehouseService.getAllWarehouses(user);

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
            Optional<String> nameFilterOpt,
            Model model
    ) {
        Optional<Iterable<Product>> productList = Optional.of(nameFilterOpt
                .filter( filter -> !filter.isEmpty())
                .flatMap(  name -> productService.findByNameContaining(name))
                .orElseGet(  () -> productService.getAllProducts().orElse(new ArrayList<>())));

        model.addAttribute("products", productList);
        model.addAttribute("filter",   nameFilterOpt);
        model.addAttribute("product",  new Product());

        return "/product/products";
    }

    @PostMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);

        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);

        return "editProduct";
    }

    @PostMapping("/{id}/edit")
    public String saveEditedProduct(@PathVariable Long id, @ModelAttribute Product editedProduct) {
        Product product = productService.getProductById(id); // TODO перенести в сервис + ui форму поправить юзабельнее
        product.setName(editedProduct.getName());
        product.setDescription(editedProduct.getDescription());
        product.setCategory(editedProduct.getCategory());
        product.setPrice(editedProduct.getPrice());
        product.setQuantity(editedProduct.getQuantity());

        productService.saveProduct(product);

        return "redirect:/products/" + id;
    }

}
