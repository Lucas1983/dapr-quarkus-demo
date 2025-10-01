package com.dapr.order.business.service;

import com.dapr.order.model.Order;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class OrderService {

  private List<Order> orders =
      List.of(new Order(1, "Customer 1"), new Order(2, "Customer 2"), new Order(3, "Customer 3"));

  public List<Order> getOrders() {
    return List.copyOf(orders);
  }

  public Order getOrder(int id) {
    return orders.stream().filter(order -> order.id().equals(id)).findFirst().orElseThrow();
  }

  public void addOrder(Order order) {
    orders.add(order);
  }

  public void deleteOrder(int id) {
    orders.removeIf(order -> order.id().equals(id));
  }
}
