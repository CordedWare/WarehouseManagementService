package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getEmployee(String firstname) {
        return employeeRepository.findAllByFirstname(firstname);
    }

    public Employee create(Employee employee) {
       return Optional.of(employee).map(w -> employeeRepository.save(w)).orElseThrow();
    }
}
