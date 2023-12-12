package ru.wms.WarehouseManagementService.service;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.wms.WarehouseManagementService.dto.RegistrationForm;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.CustomerRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTests {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @MockBean
    private CustomerRepository customerRepository;
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
        registrationForm.setNameOrg("testOrgname");
        registrationForm.setAddressOrg("testaddrORg");
        registrationForm.setContactInfoOrg("testContactInfo");
    }

    @AfterEach
    void tearDown() {
        registrationForm = null;
    }

    @Test
    void registerUserCustomer() {
        var customer = userService.registerUserCustomer(registrationForm);
        assertNotNull(customer);
        assertTrue(passwordEncoder.matches(registrationForm.getPassword(),customer.getPassword()));
        assertTrue(CoreMatchers.is(customer.getEmail()).matches(registrationForm.getEmail()));
    }
}