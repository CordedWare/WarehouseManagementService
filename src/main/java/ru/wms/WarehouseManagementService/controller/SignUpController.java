package ru.wms.WarehouseManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.service.MailSenderService;
import ru.wms.WarehouseManagementService.service.UserService;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSender;

    @GetMapping("/sign-up")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String newUserRegistration(UserRegistrationDTO userDTO) {

        if(userService.isUserExist(userDTO)){
            return "redirect:/sign-up?userExist";
        }

        var newUser = userService.addUser(userDTO);

//        mailSender.sendActivationCode(newUser);

        return "redirect:/login";
    }

}
