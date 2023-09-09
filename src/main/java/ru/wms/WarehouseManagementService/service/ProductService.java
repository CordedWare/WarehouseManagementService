package ru.wms.WarehouseManagementService.service;

import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepo;

    public List<Product> getAllProducts() {

        return productRepo.findAll();
    }

    public Product createProduct(Product product) {

        return productRepo.save(product);
    }
}
