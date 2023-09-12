package ru.wms.WarehouseManagementService.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.wms.WarehouseManagementService.security.UserPrincipal;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal UserPrincipal userPrincipal){
        var user = userPrincipal.getUser();
        System.out.println(user);
        return "index";
    }

}
