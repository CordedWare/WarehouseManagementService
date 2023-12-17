package ru.wms.WarehouseManagementService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.wms.WarehouseManagementService.dto.EmployeeRegistrationForm;
import ru.wms.WarehouseManagementService.entity.Employee;
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

    private final EmployeeService employeeService;

    @Autowired
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
            EmployeeRegistrationForm employeeRegistrationForm
    ) {
        var newEmployee = employeeService.createEmployee(employeeRegistrationForm, userPrincipal.getClient().getCompany());

//        return String.format("redirect:/login?activate_email=%s&domen=%s", newEmployee.getActivationCode(),domen);
        return "redirect:/employees";
    }

//    @PostMapping
//    public void deleteEmployee(Employee employee) {
//        employeeService.deleteEmployee(employee);
//    }

}
