package ru.wms.WarehouseManagementService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.wms.WarehouseManagementService.service.UserService;

@Controller
@RequiredArgsConstructor
public class CodeActivationController {

    /**
     * Контроллер для активации аккаунта по почте
     */

    private final UserService userService;

    @GetMapping("/activate/{code}")
    public String activateEmail(@PathVariable String code) {
        if (userService.activateUser(code))

            return "redirect:/login?activate_success";

        return "redirect:/login?activate_fail";
    }

}
