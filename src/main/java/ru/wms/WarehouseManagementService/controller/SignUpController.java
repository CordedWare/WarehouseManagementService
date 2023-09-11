package ru.wms.WarehouseManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.wms.WarehouseManagementService.configuration.SecurityConfiguration;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.entity.AppUser;
import ru.wms.WarehouseManagementService.security.Authority;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.service.UserPrincipalDetailsService;

import java.util.Collections;
import java.util.Map;

@Controller
public class SignUpController {

    public static final String USER_ROLE = "user";
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPrincipalDetailsService userPrincipalDetailsService;

    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
        model.addAttribute(USER_ROLE, new UserRegistrationDTO());

        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String newUserRegistration(AppUser user, Map<String, Object> model) {
        if (!userPrincipalDetailsService.addUser(user)) {
            model.put("message", "User exists!");

            return "sign-up";
        }
        var newUser = new AppUser();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(user.getPassword()));
        newUser.setEmail(user.getEmail());
        newUser.setAuthorities(Collections.singleton(Authority.USER));
        userPrincipalDetailsService.addUser(user);
        userRepository.save(newUser);

        return "redirect:/login";
    }

}
