package ru.wms.WarehouseManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.entity.Customer;
import ru.wms.WarehouseManagementService.service.MailSenderService;
import ru.wms.WarehouseManagementService.service.UserService;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailSenderService mailSender;

    @GetMapping("/sign-up")
    public String signUpPage(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        model.addAttribute("customer", new Customer());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String userRegistration(UserRegistrationDTO userDTO, Customer customer) {
        System.out.println(userDTO);
        System.out.println(customer);
        if(!isCorrectUserDTO(userDTO)){
            return "redirect:/sign-up?validate_error";
        }

        if(userService.isUserExist(userDTO)){
            return "redirect:/sign-up?user_exist";
        }

        var newUser = userService.registerUser(userDTO, customer);

//        mailSender.sendActivationCode(newUser);

        return String.format("redirect:/login?activate_email=%s",newUser.getActivationCode());
    }

    private boolean isCorrectUserDTO(UserRegistrationDTO userDTO){
//        TODO: проверка полей
        return true;
    }

}
