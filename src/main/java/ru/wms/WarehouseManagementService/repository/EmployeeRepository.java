package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.User;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByCustomer(User customer);
}
