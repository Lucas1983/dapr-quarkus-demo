package com.dapr.inventory.business.service;

import com.dapr.inventory.model.Product;
import com.dapr.inventory.business.repository.InventoryRepository;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class InventoryService {
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts() {
        return repository.getProducts();
    }

    public Product getProductById(String id) {
        return repository.getProductById(id);
    }

    public Product createProduct(Product product) {
        return repository.createProduct(product);
    }

    public void deleteProductById(String id) {
        repository.deleteProductById(id);
    }

    public void updateProduct(String id, Product product) {
        repository.updateProduct(id, product);
    }
}
