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
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @GetMapping("/addProduct")
    public String addProduct(
            @RequestParam(required = true) Long warehouseId,
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        var user = userPrincipal.getUser().getCompany();
        Optional<Iterable<Warehouse>> warehouseList = warehouseService.findAllById(warehouseId);

        model.addAttribute("warehouses", warehouseList);
        model.addAttribute("product", new Product());
        return "product/addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(
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

    @GetMapping("/search")
    public String getProductsByName(
            @RequestParam(
                    name = "name",
                    required = false,
                    defaultValue = "")
            Optional<String> nameFilterOpt,
            @RequestParam(
                    name = "warehouseId",
                    required = false,
                    defaultValue = "")
            Optional<Long> warehouseId,
            Model model
    ) {
        Optional<Iterable<Product>> productList = Optional.of(
                nameFilterOpt
                        .filter( filter -> !filter.isEmpty())
                        .flatMap( name  -> productService.findByNameContaining(name))
                        .orElseGet( ()  -> productService.getAllProducts().orElse(new ArrayList<>()))
        );

        for(var p : productList.get())
            System.out.println(p);
        model.addAttribute("products", productList.get());
        model.addAttribute("product",  new Product());


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

    @PostMapping("/filter")
    public String filterProducts(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(defaultValue = "asc") String sort,
            @RequestParam(defaultValue = "") String filter,
            Model model
    ) {
        Optional<Iterable<Product>> productList = productService.getAllProducts();

        productList.ifPresent( products -> {
            if (sort.equals("asc"))
                products = StreamSupport.stream(products.spliterator(), false)
                        .sorted(Comparator.comparing(product ->
                                product.getPrice()))
                        .collect(Collectors.toList());

            else if (sort.equals("desc"))
                products = StreamSupport.stream(products.spliterator(), false)
                        .sorted(Comparator.comparing( (Product product) ->
                                product.getPrice()).reversed())
                        .collect(Collectors.toList());

            model.addAttribute("products", products);
        });

        model.addAttribute("product", new Product());

        return "product/products";
    }

}
