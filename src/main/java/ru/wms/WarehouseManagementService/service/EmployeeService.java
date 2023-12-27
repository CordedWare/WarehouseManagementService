package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.EmployeeRegistrationForm;
import ru.wms.WarehouseManagementService.entity.Client;
import ru.wms.WarehouseManagementService.entity.Company;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.exceptions.UserExistException;
import ru.wms.WarehouseManagementService.repository.EmployeeRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final PasswordEncoder passwordEncoder;

    private final CompanyService companyService;

    public List<Employee> findMyEmployee(Company company) {

        return employeeRepository.findAllByCompany(company);
    }

    public void createEmployee(EmployeeRegistrationForm registrationForm, Company company) throws UserExistException {

        if (employeeRepository.existsByTelephone(registrationForm.getTelephone()) || employeeRepository.existsByEmail(registrationForm.getEmail())) {
            log.error("User exist");
            throw new UserExistException();
        }

        var employee = new Employee();

        employee.setFirstname(registrationForm.getFirstname());
        employee.setLastname(registrationForm.getLastname());
        employee.setPatronymic(registrationForm.getPatronymic());
        employee.setEmail(registrationForm.getEmail());
        employee.setTelephone(registrationForm.getTelephone());

        employee.setAuthorities(Collections.singleton(Authority.ROLE_EMPLOYEE));
        employee.setActive(false);
        employee.setActivationCode(UUID.randomUUID().toString());
        employee.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

        employee.setCompany(company);

        employeeRepository.save(employee);
    }
}
