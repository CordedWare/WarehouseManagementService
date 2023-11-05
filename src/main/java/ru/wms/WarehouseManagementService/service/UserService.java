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
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public boolean isUserExist(UserRegistrationDTO userRegistrationDTO){
        return userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent();
    }


    public User registerUser(UserRegistrationDTO userRegistrationDTO, Customer customer) {
        var newUser = new User();
        var newCustomer = new Customer();
        if (userRegistrationDTO.getUsername() != null) {
            newUser.setUsername(userRegistrationDTO.getUsername());
            newUser.setEmail(userRegistrationDTO.getEmail());
            newUser.setAuthorities(Collections.singleton(Authority.USER));
            newUser.setActive(false);
            newUser.setActivationCode(UUID.randomUUID().toString());
            newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(userRegistrationDTO.getPassword()));
        } else {
            newUser.setUsername(customer.getUser().getUsername());
            newUser.setEmail(customer.getUser().getEmail());
            newUser.setAuthorities(Collections.singleton(Authority.USER));
            newUser.setActive(false);
            newUser.setActivationCode(UUID.randomUUID().toString());
            newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(customer.getUser().getPassword()));
        }
        newCustomer.setUser(newUser);
        newCustomer.setFullName(customer.getFullName());
        newCustomer.setNameOrg(customer.getNameOrg());
        newCustomer.setContactInfoOrg(customer.getContactInfoOrg());
        newCustomer.setAddressOrg(customer.getAddressOrg());
        newCustomer.setTelephone(customer.getTelephone());

        userRepository.save(newUser);
        customerRepository.save(newCustomer);

        return newUser;
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
