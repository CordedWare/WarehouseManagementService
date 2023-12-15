package ru.wms.WarehouseManagementService.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /**
     * Базовый контроллер для расширения
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest httpServletRequest){
        var session = httpServletRequest.getSession();
        SecurityContextHolder.clearContext();
        if(session!=null)
        {
            session.invalidate();
        }

        for(var cookie : httpServletRequest.getCookies())
            cookie.setMaxAge(0);

        return "redirect:/";
    }
}
