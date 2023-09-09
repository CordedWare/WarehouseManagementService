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
import ru.wms.WarehouseManagementService.entity.Authority;
import ru.wms.WarehouseManagementService.repository.UserRepository;

import java.util.Collections;

@Controller
public class SignUpController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String newUserRegistration(@ModelAttribute UserRegistrationDTO userRegistrationDTO) {
        var newUser = new AppUser();
        newUser.setUsername(userRegistrationDTO.getUsername());
        newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(userRegistrationDTO.getPassword()));
        newUser.setAuthorities(Collections.singleton(Authority.USER));
        userRepository.save(newUser);

        return "redirect:/login";
    }

}
