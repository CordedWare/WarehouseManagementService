package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.RegistrationForm;
import ru.wms.WarehouseManagementService.entity.Client;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.ClientRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository<User, Long> userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean isUserExist( RegistrationForm registrationForm) {
        return userRepository.findByEmail(registrationForm.getEmail()).isPresent();
    }

    /**
     * Логика принципала как User с ролями и Customer как сущность заказчика разделены для атомарности.
     * TODO: Возможно придется вынести отдельно регистрацию Customer, если поменяется бизнес-логика
     */



    public Client registerClient(RegistrationForm registrationForm) {
        var client = new Client();

        client.setFirstname(registrationForm.getFirstname());
        client.setLastname(registrationForm.getLastname());
        client.setPatronymic(registrationForm.getPatronymic());
        client.setEmail(registrationForm.getEmail());
        client.setTelephone(registrationForm.getTelephone());
        client.setContactInfoOrg(registrationForm.getContactInfoOrg());

        client.setAuthorities(Collections.singleton(Authority.ROLE_CLIENT));
        client.setActive(false);
        client.setActivationCode(UUID.randomUUID().toString());
        client.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

        clientRepository.save(client);

        return client;
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
