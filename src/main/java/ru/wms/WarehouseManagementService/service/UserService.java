package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.configuration.SecurityConfiguration;
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
    private UserRepository<User,Long> userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public boolean isUserExist(UserRegistrationDTO userRegistrationDTO) {

        return userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent();
    }

    /**
     * Логика принципала как User с ролями и Customer как сущность заказчика разделены для атомарности.
     * TODO: Возможно придется вынести отдельно регистрацию Customer, если поменяется бизнес-логика
     */
    public Customer registerUserCustomer(Customer customer) {
        customer.setAuthorities(Collections.singleton(Authority.ROLE_CUSTOMER));
        customer.setActive(false);
        customer.setActivationCode(UUID.randomUUID().toString());
        customer.setPassword(SecurityConfiguration.passwordEncoder().encode(customer.getPassword()));
        customerRepository.save(customer);
        return customer;
    }


    public boolean activateUser(String code) {
        var optionalUser = userRepository.findByActivationCode(code);

        if (optionalUser.isPresent()) {
            var user = optionalUser.get();

            user.setActive(true);
            user.setActivationCode(null);

            userRepository.save(user);
        }

        return optionalUser.isPresent();
    }
}
