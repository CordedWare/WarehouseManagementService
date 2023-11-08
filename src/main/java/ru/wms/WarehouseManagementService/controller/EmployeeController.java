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
        model.addAttribute("employee", new Employee());
        return "/employees";
    }

    @PostMapping
    public String createEmployee(Employee employee) {
        var newEmployee = employeeService.createEmployee(employee);

        return String.format("redirect:/login?activate_email=%s", newEmployee.getActivationCode());
    }

//    @PostMapping
//    public void deleteEmployee(Employee employee) {
//        employeeService.deleteEmployee(employee);
//    }


}
