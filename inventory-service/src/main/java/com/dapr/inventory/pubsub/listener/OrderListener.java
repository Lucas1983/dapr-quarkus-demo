package com.dapr.inventory.pubsub.listener;

import com.dapr.common.DaprConfig;
import com.dapr.common.order.OrderCanceledEvent;
import com.dapr.common.order.OrderCompletedEvent;
import com.dapr.common.order.OrderCreatedEvent;
import com.dapr.inventory.business.service.InventoryService;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderListener {

  @Inject InventoryService inventoryService;

  @POST
  @Path("/order-created")
  @Topic(name = DaprConfig.ORDER_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderCreated(CloudEvent<OrderCreatedEvent> order) {

    log.info("Received ORDER CREATED event{}", order.getData().orderId());
    if (inventoryService.reserveInventory(order.getData().orderId(), order.getData().products())) {
      log.info("Inventory reserved for order {}", order.getData().orderId());
    } else {
      log.error("Inventory reservation failed for order {}", order.getData().orderId());
    }
  }

  @POST
  @Path("/order-completed")
  @Topic(name = DaprConfig.ORDER_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderCompleted(CloudEvent<OrderCompletedEvent> order) {

    log.info("Received ORDER COMPLETED event{}", order.getData().orderId());
    // TODO: Implement inventory completion logic - update inventory stock
  }

  @POST
  @Path("/order-cancelled")
  @Topic(name = DaprConfig.ORDER_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderCancelled(CloudEvent<OrderCanceledEvent> event) {

    log.info("Received ORDER CANCELLED event {}", event.getData().orderId());
    // TODO: Implement inventory cancellation logic - release reserved inventory
  }
}
