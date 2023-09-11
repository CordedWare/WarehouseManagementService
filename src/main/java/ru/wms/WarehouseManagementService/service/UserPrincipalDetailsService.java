package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.wms.WarehouseManagementService.entity.AppUser;
import ru.wms.WarehouseManagementService.security.Authority;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.repository.UserRepository;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSender;

    public boolean activateUser(String code) {
        AppUser user = userRepository.findByActivationCode(code);
        if (user == null) {
            return false;
        }
        user.setActivationCode(null);

        userRepository.save(user);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new UserPrincipal(user);
    }

    public boolean addUser(AppUser user) {
        AppUser userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setAuthorities(Collections.singleton(Authority.USER));
        user.setActivationCode(UUID.randomUUID().toString());

        userRepository.save(user);

        sendMessage(user);

        return true;
    }

    public void sendMessage(AppUser user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Добро пожаловать в сервис WarehouseManagementWebservice. Пожалуйста, перейдите по ссылке: http://localhost:8080/index/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }
}