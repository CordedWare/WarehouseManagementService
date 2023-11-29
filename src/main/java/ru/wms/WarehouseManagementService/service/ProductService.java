package ru.wms.WarehouseManagementService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.Employee;
import ru.wms.WarehouseManagementService.entity.Product;
import ru.wms.WarehouseManagementService.entity.User;
import ru.wms.WarehouseManagementService.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(id);

        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());

            return productRepository.save(existingProduct);
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }

    public Iterable<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Iterable<Product> getAllMyProducts(User user) {
        if(user instanceof Employee employee)
            return productRepository.findAllByOwner(employee.getCustomer());

        return productRepository.findAllByOwner(user);
    }

    public Product saveProduct(Product product) {

        return productRepository.save(product);
    }

    public List<Product> getProductsByName(String name) {

        return productRepository.findByName(name);
    }

    public void deleteProductById(Long id) {

        productRepository.deleteById(id);
    }


}