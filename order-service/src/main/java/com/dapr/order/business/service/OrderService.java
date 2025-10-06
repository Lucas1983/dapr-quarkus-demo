package com.dapr.order.business.service;

import com.dapr.order.business.repository.OrderRepository;
import com.dapr.order.config.DaprConfig;
import com.dapr.order.model.Order;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class OrderService {
  @Inject OrderRepository orderRepository;

  @Inject SyncDaprClient dapr;

  public List<Order> getOrders() {
    return orderRepository.getOrders();
  }

  public Order getOrder(UUID id) {
    return orderRepository.getOrder(id);
  }

  public void createOrder(Order order) {
    orderRepository.createOrder(order);
    dapr.publishEvent(DaprConfig.PUBSUB_NAME, DaprConfig.ORDER_TOPIC, order);
  }

  public void deleteOrder(UUID id) {
    orderRepository.deleteOrder(id);
  }
}
