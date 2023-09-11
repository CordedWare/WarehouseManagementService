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
        return userRepository.findByEmail(userRegistrationDTO.getEmail()) != null;
    }


    public User addUser(UserRegistrationDTO userDTO) {
        var newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        newUser.setAuthorities(Collections.singleton(Authority.USER));

        newUser.setActive(true);
        newUser.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(newUser);

        return newUser;
    }




    public boolean activateUser(String code) {
        var user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActive(true);
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

}
