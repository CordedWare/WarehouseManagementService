package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.dto.EmployeeRegistrationForm;
import ru.wms.WarehouseManagementService.entity.Client;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.repository.EmployeeRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CompanyService companyService;

    public List<Employee> findMyEmployee(Client client) {

        return employeeRepository.findAllByCompany(client.getCompany());
    }

    public Employee createEmployee(EmployeeRegistrationForm registrationForm, Client client) {

        var employee = new Employee();

        employee.setFirstname(registrationForm.getFirstname());
        employee.setLastname(registrationForm.getLastname());
        employee.setPatronymic(registrationForm.getPatronymic());
        employee.setEmail(registrationForm.getEmail());

        employee.setAuthorities(Collections.singleton(Authority.ROLE_EMPLOYEE));
        employee.setActive(false);
        employee.setActivationCode(UUID.randomUUID().toString());
        employee.setPassword(passwordEncoder.encode(registrationForm.getPassword()));

//        client.getCompany().getEmployess().add(client);
        employee.setCompany(client.getCompany());

        employeeRepository.save(employee);

        return employee;
    }

    public void deleteEmployee(Employee employee) {
//        userRepository.delete(employee.getUser());
//        employeeRepository.delete(employee);
    }

}
