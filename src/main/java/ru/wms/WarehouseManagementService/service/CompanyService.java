package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.CompanyCreateForm;
import ru.wms.WarehouseManagementService.entity.Client;
import ru.wms.WarehouseManagementService.entity.Company;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.CompanyRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    private final UserRepository<User, Long> userRepository;

    public Company createCompany(CompanyCreateForm companyCreateForm, Client client){
        var company = new Company();
        company.setName(companyCreateForm.getName());
        company.setAddress(companyCreateForm.getAddress());

        if(company.getUsers() == null){
            company.setUsers(new HashSet<>());
        }
        company.getUsers().add(client);
        client.setCompany(company);

        companyRepository.save(company);
        userRepository.save(client);

        return company;
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }
}
