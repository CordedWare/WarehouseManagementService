package ru.wms.WarehouseManagementService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/products")
    public String products(){
        return "products";
    }

    @GetMapping("/orders")
    public String orders(){
        return "orders";
    }

    @GetMapping("/warehouses")
    public String warehouses(){
        return "warehouses";
    }

}
