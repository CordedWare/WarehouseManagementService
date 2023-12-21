package ru.wms.WarehouseManagementService.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import ru.wms.WarehouseManagementService.controller.product.ProductController;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.security.UserPrincipal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Model model;

    @Test
    public void testFilterSortProducts() {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.getUser();

        List<Product> products = new ArrayList<>();
        products.add(
                new Product(null, "Product 1", "description", "tech",
                        new BigDecimal("20.0"), 3, LocalDate.now(), null, null));
        products.add(
                new Product(null, "Product 2", "description", "tech",
                        new BigDecimal("10.0"), 2, LocalDate.now(), null, null));
        products.add(
                new Product(null, "Product 3", "description", "tech",
                        new BigDecimal("12.0"), 1, LocalDate.now(), null, null));

        Optional<Iterable<Product>> productList = Optional.of(products);

        when(productService.getAllProducts()).thenReturn(productList);

        String result = productController.filterProducts(userPrincipal, "asc", "", model);

        assertEquals("product/products", result);

        verify(productService, times(1)).getAllProducts();

        ArgumentCaptor<Iterable<Product>> captor = ArgumentCaptor.forClass(Iterable.class);
        verify(model, times(1)).addAttribute(eq("products"), captor.capture());

        Iterable<Product> capturedProducts = captor.getValue();
        assertEquals(3, capturedProducts.spliterator().getExactSizeIfKnown());
    }
}