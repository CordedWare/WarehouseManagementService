package ru.wms.WarehouseManagementService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.wms.WarehouseManagementService.dto.RegistrationForm;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.service.MailSenderService;
import ru.wms.WarehouseManagementService.service.UserService;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    /**
     * Контроллер регистрации
     */

    @Value("${domen}")
    private String domen;

    private final UserService userService;

    private final MailSenderService mailSender;

    @GetMapping("/sign-up")
    public String signUpPage(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String userRegistration(RegistrationForm registrationForm) {
        if(userService.isUserExist(registrationForm))
            return "redirect:/sign-up?user_exist";

        var newUser = userService.registerClient(registrationForm);

        return String.format("redirect:/login?activate_email=%s&domen=%s", newUser.getActivationCode(),domen);
    }
}
