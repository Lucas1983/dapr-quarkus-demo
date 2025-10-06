package com.dapr.order.business.service;

import static com.dapr.order.model.OrderStatus.NEW;

import com.dapr.order.business.repository.OrderRepository;
import com.dapr.order.config.DaprConfig;
import com.dapr.order.model.Order;
import com.dapr.order.model.OrderStatus;
import com.dapr.order.web.controller.dto.CreateOrderDto;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

  public void createOrder(CreateOrderDto dto) {

    Order order =
        Order.builder()
            .orderId(UUID.randomUUID())
            .customerId(dto.getCustomerId())
            .products(dto.getProducts() != null ? dto.getProducts() : Map.of())
            .orderStatus(NEW)
            .createdAt(Instant.now())
            .build();

    orderRepository.saveOrder(order);
    log.info("Created order {}", order);
  }

  public void updateOrderStatus(UUID id, OrderStatus orderStatus) {

    Optional.of(orderRepository.getOrder(id))
        .ifPresentOrElse(
            order -> {
              order.setOrderStatus(orderStatus);
              orderRepository.saveOrder(order);
              log.info("Updated order {}", order);

              switch (order.getOrderStatus()) {
                case PROCESSING -> log.info("Order {} is being processed", order.getOrderId());
                case COMPLETED -> {
                  log.info("Order {} has been completed", order.getOrderId());
                  dapr.publishEvent(DaprConfig.PUBSUB_NAME, DaprConfig.ORDER_TOPIC, order);
                  log.info("Published order COMPLETED event for {}", order);
                }
                case CANCELLED -> {
                  log.info("Order {} has been cancelled", order.getOrderId());
                  dapr.publishEvent(DaprConfig.PUBSUB_NAME, DaprConfig.ORDER_TOPIC, order);
                  log.info("Published order CANCELLED event for {}", order);
                }
                default -> {
                  // No action needed for other statuses
                }
              }
            },
            RuntimeException::new);
  }

  public void removeOrder(UUID id) {
    orderRepository.deleteOrder(id);
  }
}
