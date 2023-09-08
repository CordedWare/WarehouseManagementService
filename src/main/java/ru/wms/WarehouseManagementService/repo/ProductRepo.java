package ru.wms.WarehouseManagementService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

}