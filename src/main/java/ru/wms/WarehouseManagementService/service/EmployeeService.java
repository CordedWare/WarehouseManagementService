package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.configuration.SecurityConfiguration;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.EmployeeRepository;
import ru.wms.WarehouseManagementService.repository.UserRepository;
import ru.wms.WarehouseManagementService.security.Authority;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public List<Employee> findMyEmployee(User user) {
        return employeeRepository.findAllByCustomer(user);
    }

    public User createEmployee(Employee employee, User customer) {
        var newUser = new User();
        var newEmployee = new Employee();

        newUser.setUsername(employee.getUser().getUsername());
        newUser.setEmail(employee.getUser().getEmail());
        newUser.setAuthorities(Collections.singleton(Authority.ROLE_EMPLOYEE));
        newUser.setActive(false);
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setPassword(SecurityConfiguration.passwordEncoder().encode(employee.getUser().getPassword()));

        newEmployee.setUser(newUser);
        newEmployee.setFirstname(employee.getFirstname());
        newEmployee.setLastname(employee.getLastname());

        newEmployee.setCustomer(customer);

        userRepository.save(newUser);
        employeeRepository.save(newEmployee);

        return newUser;
    }

    public void deleteEmployee(Employee employee) {
        userRepository.delete(employee.getUser());
        employeeRepository.delete(employee);
    }
}
