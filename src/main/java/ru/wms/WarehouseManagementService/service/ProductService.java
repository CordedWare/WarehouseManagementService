package ru.wms.WarehouseManagementService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.wms.WarehouseManagementService.entity.*;
import ru.wms.WarehouseManagementService.exceptions.NotFoundWarehouseException;
import ru.wms.WarehouseManagementService.exceptions.OverflowWarehouse;
import ru.wms.WarehouseManagementService.exceptions.WarehouseException;
import ru.wms.WarehouseManagementService.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final WarehouseService warehouseService;

    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow( () ->
                        new RuntimeException("Товар не был найден по id: " + id));
    }

    public Product updateProduct(Long id, Product editedProduct) {

        return productRepository.findById(id)
                .map(product -> {
                    product.setName(editedProduct.getName());
                    product.setDescription(editedProduct.getDescription());
                    product.setCategory(editedProduct.getCategory());
                    product.setPrice(editedProduct.getPrice());
                    product.setQuantity(editedProduct.getQuantity());

                    return productRepository.save(product);
                })
                .orElseThrow( () ->
                        new RuntimeException("Товар не был найден по id: " + id));
    }


    public Optional<Iterable<Product>> getCompanyProducts(Company company) {
        var warehouseList = warehouseService.getCompanyWarehouse(company).get();

        var products = new ArrayList<Product>();

        for (var wh : warehouseList){
            products.addAll(wh.getProductList() );
        }

        return Optional.of(products);
    }

    public void move(Set<Product> products, Optional<Warehouse> warehouse) {
        products.forEach( product ->
                product.setWarehouse(warehouse.get()));

        productRepository.saveAll(products);
    }

    public void createProduct(Product product, Long warehouseId) throws WarehouseException {
        var warehouseOpt = warehouseService.getById(warehouseId);

        if(warehouseOpt.isEmpty())
            throw new NotFoundWarehouseException("Склад не найден");

        if(product.getQuantity()<0)
            throw new IllegalArgumentException();

        var warehouse = warehouseOpt.get();
        product.setWarehouse(warehouse);

        if(warehouse.getCapacity() < product.getQuantity())
            throw new OverflowWarehouse("Склад не может вместить столько товаров");

        productRepository.save(product);
    }

    public Optional<Iterable<Product>> getAllProducts() {

        return Optional.ofNullable(productRepository.findAll());
    }

    public Optional<Iterable<Product>> findByNameContaining(String name) {

        return Optional.ofNullable(productRepository.findByNameContaining(name));
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public void saveParserProduct(List<Product> products,Long warehouseId) throws WarehouseException {
        for (var p : products)
            createProduct(p,warehouseId);
    }

}
