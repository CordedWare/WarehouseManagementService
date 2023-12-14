package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
}
