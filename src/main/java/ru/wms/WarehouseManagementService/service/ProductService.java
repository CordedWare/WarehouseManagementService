package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.*;
import ru.wms.WarehouseManagementService.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isPresent()) {

            return productOpt.get();
        } else {
            throw new RuntimeException("Товар не был найден по id: " + id);
        }
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOpt = productRepository.findById(id);

        if (existingProductOpt.isPresent()) {
            Product exisProdOpt = existingProductOpt.get();
            exisProdOpt.setName(updatedProduct.getName());
            exisProdOpt.setDescription(updatedProduct.getDescription());
            exisProdOpt.setPrice(updatedProduct.getPrice());

            return productRepository.save(exisProdOpt);
        } else {
            throw new RuntimeException("Товар не был найден по id: " + id);
        }
    }

    public Optional<Iterable<Product>> getAllMyProducts(User user) {
        if (user instanceof Employee employee)

            return Optional.ofNullable(productRepository.findAllByOwner(employee.getCustomer()));

        return Optional.ofNullable(productRepository.findAllByOwner(user));
    }

    public void move(Set<Product> products, Optional<Warehouse> warehouse) {
        products.forEach( product ->
                product.setWarehouse(warehouse.get()));

        productRepository.saveAll(products);
    }

    public void createProduct(Product product, Long warehouseId, User user) {
        var warehouse = warehouseService.getById(warehouseId);
        product.setOwner(user);
        product.setWarehouse(warehouse.get());

        productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<List<Product>> getProductsByName(String name) {

        return productRepository.findByName(name);
    }

    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    public Iterable<Product> getAllProducts() {

        return productRepository.findAll();
    }

}