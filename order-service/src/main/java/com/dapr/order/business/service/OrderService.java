package com.dapr.order.business.service;

import com.dapr.order.business.repository.OrderRepository;
import com.dapr.order.model.Order;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class OrderService {
  @Inject OrderRepository orderRepository;

  public List<Order> getOrders() {
    return orderRepository.getOrders();
  }

  public Order getOrder(UUID id) {
    return orderRepository.getOrder(id);
  }

  public void createOrder(Order order) {
    orderRepository.createOrder(order);
  }

  public void deleteOrder(UUID id) {
    orderRepository.deleteOrder(id);
  }
}
