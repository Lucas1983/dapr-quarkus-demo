package com.dapr.order.business.service;

import static com.dapr.common.DaprConfig.ORDER_TOPIC;
import static com.dapr.common.DaprConfig.PUBSUB_NAME;
import static com.dapr.order.model.dictionary.OrderStatus.NEW;

import com.dapr.common.order.OrderCanceledEvent;
import com.dapr.common.order.OrderCompletedEvent;
import com.dapr.common.order.OrderCreatedEvent;
import com.dapr.order.business.repository.OrderRepository;
import com.dapr.order.model.dictionary.OrderStatus;
import com.dapr.order.model.entity.Order;
import com.dapr.order.web.dto.CreateOrderDto;
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

    log.info("Created order : {}", order);
    var event =
        OrderCreatedEvent.builder()
            .orderId(order.getOrderId())
            .products(order.getProducts())
            .build();
    dapr.publishEvent(PUBSUB_NAME, ORDER_TOPIC, event);
    orderRepository.saveOrder(order);
    log.info("Published ORDER CREATED event : {}", event);
  }

  public void updateOrderStatus(UUID id, OrderStatus orderStatus) {

    Optional.of(orderRepository.getOrder(id))
        .ifPresentOrElse(
            order -> {
              order.setOrderStatus(orderStatus);
              orderRepository.saveOrder(order);
              log.info("Updated order {}", order);

              switch (order.getOrderStatus()) {
                case PROCESSING -> log.info("Order is being processed : {}", order);
                case COMPLETED -> {
                  log.info("Order has been completed : {}", order);
                  var event =
                      OrderCompletedEvent.builder()
                          .orderId(order.getOrderId())
                          .customerId(order.getCustomerId())
                          .build();
                  dapr.publishEvent(PUBSUB_NAME, ORDER_TOPIC, event);
                  log.info("Published order COMPLETED event : {}", event);
                }
                case CANCELLED -> {
                  log.info("Order has been cancelled : {}", order);
                  var event = OrderCanceledEvent.builder().orderId(order.getOrderId()).build();
                  dapr.publishEvent(PUBSUB_NAME, ORDER_TOPIC, event);
                  log.info("Published order CANCELLED event : {}", event);
                }
                default ->
                    throw new IllegalStateException(
                        "Unexpected order status: " + order.getOrderStatus());
              }
            },
            () -> {
              throw new RuntimeException("Order not found: " + id);
            });
  }

  public void removeOrder(UUID id) {
    orderRepository.deleteOrder(id);
  }
}
