package com.dapr.inventory.business.repository;

import com.dapr.inventory.model.Product;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class InventoryRepository {
    public List<Product> getProducts() {
        // TODO: Implement data access logic
        return null;
    }

    public Product getProductById(String id) {
        // TODO: Implement data access logic
        return null;
    }

    public Product createProduct(Product product) {
        // TODO: Implement data access logic
        return null;
    }

    public void deleteProductById(String id) {
        // TODO: Implement data access logic
    }

    public void updateProduct(String id, Product product) {
        // TODO: Implement data access logic
    }
}
