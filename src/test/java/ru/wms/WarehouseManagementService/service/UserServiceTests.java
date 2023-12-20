package ru.wms.WarehouseManagementService.service;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import ru.wms.WarehouseManagementService.controller.product.ProductController;
import ru.wms.WarehouseManagementService.dto.RegistrationForm;
import ru.wms.WarehouseManagementService.entity.Client;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.repository.ClientRepository;
import ru.wms.WarehouseManagementService.repository.ProductRepository;
import ru.wms.WarehouseManagementService.security.Authority;
import ru.wms.WarehouseManagementService.security.UserPrincipal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTests {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @MockBean
    private ClientRepository clientRepository;
    private RegistrationForm registrationForm;
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private Model model;

    @Autowired
    public UserServiceTests(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        registrationForm = new RegistrationForm();
        registrationForm.setFirstname("testFirstname");
        registrationForm.setLastname("testLastname");
        registrationForm.setPatronymic("testPatronymic");
        registrationForm.setPassword("password");
        registrationForm.setEmail("testEmail@email.com");
        registrationForm.setTelephone(79028196791L);
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void registerUserCustomer() {
        var customer = userService.registerClient(registrationForm);

        assertNotNull(customer);
        assertTrue(passwordEncoder.matches(registrationForm.getPassword(), customer.getPassword()));
        assertTrue(CoreMatchers.is(customer.getEmail()).matches(registrationForm.getEmail().toLowerCase()));
        assertTrue(CoreMatchers.is(customer.getFirstname()).matches(registrationForm.getFirstname()));
        assertTrue(CoreMatchers.is(customer.getLastname()).matches(registrationForm.getLastname()));
        assertTrue(CoreMatchers.is(customer.getPatronymic()).matches(registrationForm.getPatronymic()));
        assertTrue(CoreMatchers.is(customer.getTelephone()).matches(registrationForm.getTelephone()));
        assertTrue(CoreMatchers.is(customer.isActive()).matches(false));
        assertEquals(customer.getAuthorities(), Collections.singleton(Authority.ROLE_CLIENT));

        assertNotNull(customer.getActivationCode());

        verify(clientRepository, times(1))
                .save(Mockito.any(Client.class));
    }

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
