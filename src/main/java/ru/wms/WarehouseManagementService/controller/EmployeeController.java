package ru.wms.WarehouseManagementService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.service.EmployeeService;

import java.util.List;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String getEmployee(Model model) {
       model.addAttribute("employees", employeeService.findAllEmployee());
        return "/employees";
    }

    @PostMapping
    public String createEmployee(UserRegistrationDTO userRegistrationDTO, Employee employee) {
        employeeService.createEmployee(employee);
        return "redirect:/employees";
    }


}
