package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.configuration.SecurityConfiguration;
import ru.wms.WarehouseManagementService.dto.RegistrationForm;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.entity.Customer;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.CustomerRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository<User, Long> userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public boolean isUserExist(UserRegistrationDTO userRegistrationDTO) {

        return userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent();
    }

    /**
     * Логика принципала как User с ролями и Customer как сущность заказчика разделены для атомарности.
     * TODO: Возможно придется вынести отдельно регистрацию Customer, если поменяется бизнес-логика
     */

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Customer registerUserCustomer(RegistrationForm registrationForm) {
        var customer = new Customer();

        customer.setFirstname(registrationForm.getFirstname());
        customer.setLastname(registrationForm.getLastname());
        customer.setPatronymic(registrationForm.getPatronymic());
        customer.setEmail(registrationForm.getEmail());
        customer.setTelephone(registrationForm.getTelephone());
        customer.setNameOrg(registrationForm.getNameOrg());
        customer.setAddressOrg(registrationForm.getAddressOrg());
        customer.setContactInfoOrg(registrationForm.getContactInfoOrg());

        customer.setAuthorities(Collections.singleton(Authority.ROLE_CUSTOMER));
        customer.setActive(false);
        customer.setActivationCode(UUID.randomUUID().toString());
        customer.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

        customerRepository.save(customer);

        return customer;
    }

    public boolean activateUser(String code) {
        var userOpt = userRepository.findByActivationCode(code);

        if (userOpt.isPresent()) {
            var user = userOpt.get();

            user.setActive(true);
            user.setActivationCode(null);

            userRepository.save(user);
        }

        return userOpt.isPresent();
    }

}
