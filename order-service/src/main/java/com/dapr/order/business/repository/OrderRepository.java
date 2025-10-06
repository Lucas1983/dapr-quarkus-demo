package com.dapr.order.business.repository;

import com.dapr.order.config.DaprConfig;
import com.dapr.order.model.Order;
import io.dapr.client.domain.State;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class OrderRepository {

  @Inject SyncDaprClient dapr;

  public List<Order> getOrders() {
    return dapr.getBulkState(DaprConfig.STATE_STORE_NAME, List.of(), Order.class).stream()
        .map(State::getValue)
        .toList();
  }

  public Order getOrder(UUID id) {
    return dapr.getState(DaprConfig.STATE_STORE_NAME, String.valueOf(id), Order.class).getValue();
  }

  public void saveOrder(Order order) {
    dapr.saveState(DaprConfig.STATE_STORE_NAME, String.valueOf(order.getOrderId()), order);
  }

  public void deleteOrder(UUID id) {
    dapr.deleteState(DaprConfig.STATE_STORE_NAME, String.valueOf(id));
  }
}
