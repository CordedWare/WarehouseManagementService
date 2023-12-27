package ru.wms.WarehouseManagementService.controller.company;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.wms.WarehouseManagementService.dto.CompanyCreateForm;
import ru.wms.WarehouseManagementService.security.UserPrincipal;
import ru.wms.WarehouseManagementService.service.CompanyService;

@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {


    private final CompanyService companyService;

    @GetMapping("/manage")
    public String manage(Model model,
                         @AuthenticationPrincipal UserPrincipal userPrincipal){
        model.addAttribute("company",userPrincipal.getUser().getCompany());
        return "company/manage";
    }


    @GetMapping("/manage/create")
    public String create(Model model){
        model.addAttribute("companyCreateForm", new CompanyCreateForm());
        return "company/create";
    }
    @PostMapping("/manage/create")
    public String createCompany(CompanyCreateForm companyCreateForm,
                                @AuthenticationPrincipal UserPrincipal userPrincipal){
        companyService.createCompany(companyCreateForm,userPrincipal.getClient());
        return "redirect:/company/manage";
    }


    @GetMapping("/manage/edit")
    public String edit(){
        return "company/edit";
    }
}
