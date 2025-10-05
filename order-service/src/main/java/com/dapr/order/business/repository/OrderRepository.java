package com.dapr.order.business.repository;

import com.dapr.order.model.Order;
import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class OrderRepository {
  private final List<Order> orders = new ArrayList<>();

  public List<Order> getOrders() {
    return List.copyOf(orders);
  }

  public Order getOrder(UUID id) {
    return orders.stream().filter(order -> order.getOrderId().equals(id)).findFirst().orElseThrow();
  }

  public void createOrder(Order order) {
    orders.add(order);
  }

  public void deleteOrder(UUID id) {
    orders.removeIf(order -> order.getOrderId().equals(id));
  }
}
