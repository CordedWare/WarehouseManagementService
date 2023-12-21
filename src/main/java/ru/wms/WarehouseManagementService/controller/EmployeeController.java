package ru.wms.WarehouseManagementService.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.wms.WarehouseManagementService.dto.EmployeeRegistrationForm;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.exceptions.UserExistException;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    /**
     * Контроллер сотрудника
     */

    @Value("${domen}")
    private String domen;

    @Autowired
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getEmployee(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            Model model
    ) {
        model.addAttribute("employees", employeeService.findMyEmployee(userPrincipal.getClient().getCompany()));
        model.addAttribute("employeeRegistrationForm", new EmployeeRegistrationForm());

        return "employees";
    }

    @PostMapping("/create")
    public String createEmployee(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @Valid EmployeeRegistrationForm employeeRegistrationForm,
            BindingResult bindingResult
    ) {
        try {
            if (!bindingResult.hasErrors())
                 employeeService.createEmployee(employeeRegistrationForm, userPrincipal.getClient().getCompany());
        } catch (Exception e) {
            return "redirect:/employees?alreadyExists";
        }

        return "redirect:/employees";
    }



}
