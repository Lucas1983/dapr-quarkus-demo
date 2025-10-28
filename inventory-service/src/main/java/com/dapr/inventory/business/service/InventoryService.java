package com.dapr.inventory.business.service;

import static com.dapr.common.DaprConfig.INVENTORY_TOPIC;
import static com.dapr.common.DaprConfig.PUBSUB_NAME;

import com.dapr.common.inventory.InventoryReservationFailedEvent;
import com.dapr.common.inventory.InventoryReservationSucceededEvent;
import com.dapr.inventory.business.repository.InventoryRepository;
import com.dapr.inventory.model.entity.Product;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class InventoryService {

  @Inject InventoryRepository repository;

  @Inject SyncDaprClient dapr;

  public List<Product> getProducts() {
    return repository.getProducts();
  }

  public Product getProductById(String id) {
    return repository.getProductById(id);
  }

  public void createProduct(Product product) {
    repository.saveProduct(product);
  }

  public void deleteProductById(String id) {
    repository.deleteProduct(id);
  }

  public void updateProduct(Product product) {
    repository.saveProduct(product);
  }

  public boolean reserveInventory(UUID orderId, Map<UUID, Integer> products) {

	  log.info("Reserving inventory for order {} with products {}", orderId, products);
    // TODO: Implement inventory reservation logic - check availability + reserve inventory
    if (orderId == null || products == null || products.isEmpty()) {
      var event = InventoryReservationFailedEvent.builder().orderId(orderId).build();
      dapr.publishEvent(PUBSUB_NAME, INVENTORY_TOPIC, event);
      log.info("Published INVENTORY RESERVATION FAILED event : {}", event);
      return false;
    }
    var event = InventoryReservationSucceededEvent.builder().orderId(orderId).build();
    dapr.publishEvent(PUBSUB_NAME, INVENTORY_TOPIC, event);
    log.info("Published INVENTORY RESERVATION SUCCEED event : {}", event);
    return true;
  }

  public void confirmInventory(UUID uuid) {
    log.info("Confirmed inventory for order {}", uuid);
    // TODO: Implement inventory confirmation logic - deduct reserved inventory + handle errors
  }
}
