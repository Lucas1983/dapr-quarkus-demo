package com.dapr.inventory.business.repository;

import com.dapr.common.DaprConfig;
import com.dapr.inventory.model.entity.Product;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

@Singleton
public class InventoryRepository {
  @Inject SyncDaprClient dapr;

  public List<Product> getProducts() {

    return dapr.getBulkState(DaprConfig.STATE_STORE_NAME, List.of(), Product.class).stream()
        .map(io.dapr.client.domain.State::getValue)
        .toList();
  }

  public Product getProductById(String id) {

    return dapr.getState(DaprConfig.STATE_STORE_NAME, id, Product.class).getValue();
  }

  public void saveProduct(Product product) {
    dapr.saveState(DaprConfig.STATE_STORE_NAME, String.valueOf(product.getId()), product);
  }

  public void deleteProduct(String id) {
    dapr.deleteState(DaprConfig.STATE_STORE_NAME, id);
  }
}
