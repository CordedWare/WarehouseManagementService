package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.configuration.SecurityConfiguration;
import ru.wms.WarehouseManagementService.entity.Customer;
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

    public List<Employee> findMyEmployee(Customer customer) {

        return employeeRepository.findAllByCustomer(customer);
    }

    public Employee createEmployee(Employee employee, Customer customer) {

        employee.setAuthorities(Collections.singleton(Authority.ROLE_EMPLOYEE));
        employee.setActive(false);
        employee.setActivationCode(UUID.randomUUID().toString());
        employee.setPassword(SecurityConfiguration.passwordEncoder().encode(employee.getPassword()));
        employee.setCustomer(customer);

        employeeRepository.save(employee);

        return employee;
    }

    public void deleteEmployee(Employee employee) {
//        userRepository.delete(employee.getUser());
//        employeeRepository.delete(employee);
    }
}
