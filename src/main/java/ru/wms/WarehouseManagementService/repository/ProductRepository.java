package ru.wms.WarehouseManagementService.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.wms.WarehouseManagementService.entity.Company;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.User;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Iterable<Product> findAll();

    List<Product> findByNameContaining(String name);

    List<Product> findAllByCompany(Company company);

}