package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.configuration.SecurityConfiguration;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean isUserExist(UserRegistrationDTO userRegistrationDTO){
        return userRepository.findByEmail(userRegistrationDTO.getEmail()).isPresent();
    }


    public User registerUser(UserRegistrationDTO userDTO) {
        var newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setAuthorities(Collections.singleton(Authority.USER));
        newUser.setActive(false);
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(userDTO.getPassword()));

        userRepository.save(newUser);

        return newUser;
    }


    public boolean activateUser(String code) {
        var optionalUser = userRepository.findByActivationCode(code);
        if (optionalUser.isEmpty()) {
            return false;
        }
        var user = optionalUser.get();

        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);
        return true;
    }
}
