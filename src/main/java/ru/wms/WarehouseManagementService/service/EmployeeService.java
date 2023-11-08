package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.configuration.SecurityConfiguration;
import ru.wms.WarehouseManagementService.dto.UserRegistrationDTO;
import ru.wms.WarehouseManagementService.entity.Customer;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.EmployeeRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public List<Employee> findAllEmployee() {
        return employeeRepository.findAll();
    }

    public Employee create(Employee employee) {
        return Optional.of(employee).map(w -> employeeRepository.save(w)).orElseThrow();
    }

    public User createEmployee(Employee employee) {
        var newUser = new User();
        var newEmployee = new Employee();

        newUser.setUsername(employee.getUsername());
        newUser.setEmail(employee.getEmail());
        newUser.setAuthorities(Collections.singleton(Authority.EMPLOYEE));
        newUser.setActive(false);
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(employee.getPassword()));

        newEmployee.setUser(newUser);
        newEmployee.setUsername(employee.getUsername());
        newEmployee.setEmail(employee.getEmail());
        newEmployee.setFirstname(employee.getFirstname());
        newEmployee.setLastname(employee.getLastname());
        newEmployee.setPassword(employee.getPassword());

        userRepository.save(newUser);
        employeeRepository.save(newEmployee);

        return newUser;
    }
}
