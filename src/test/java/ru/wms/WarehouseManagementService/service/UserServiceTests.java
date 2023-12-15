package ru.wms.WarehouseManagementService.service;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.wms.WarehouseManagementService.dto.RegistrationForm;
import ru.wms.WarehouseManagementService.entity.Client;
import ru.wms.WarehouseManagementService.repository.ClientRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTests {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @MockBean
    private ClientRepository clientRepository;
    private RegistrationForm registrationForm;

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

        Mockito.verify(clientRepository, Mockito.times(1))
                .save(Mockito.any(Client.class));
    }
}