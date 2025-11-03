package com.dapr.inventory.pubsub.listener;

import com.dapr.common.DaprConfig;
import com.dapr.common.order.OrderCanceledEvent;
import com.dapr.common.order.OrderCompletedEvent;
import com.dapr.common.order.OrderCreatedEvent;
import com.dapr.inventory.business.service.InventoryService;
import io.dapr.Rule;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Path("/orders")
public class OrderListener {

  @Inject InventoryService inventoryService;

  @POST
  @Path("/default")
  public void onUnknown(CloudEvent<Object> event) {
    log.info("Received unknown event{}", event.getData());
  }

  @POST
  @Path("/created")
  @Topic(
      name = DaprConfig.ORDER_TOPIC,
      pubsubName = DaprConfig.PUBSUB_NAME,
      rule = @Rule(match = "event.data.type == 'ORDER_CREATED'", priority = 0))
  public void onOrderCreated(CloudEvent<OrderCreatedEvent> event) {

    log.info("Received ORDER CREATED event : {}", event);
    if (inventoryService.reserveInventory(
        event.getData().getOrderId(), event.getData().getProducts())) {
      log.info("Inventory reserved for order : {}", event.getData());
    } else {
      log.error("Inventory reservation failed for order : {}", event.getData());
    }
  }

  @POST
  @Path("/completed")
  @Topic(
      name = DaprConfig.ORDER_TOPIC,
      pubsubName = DaprConfig.PUBSUB_NAME,
      rule = @Rule(match = "event.data.type == 'ORDER_COMPLETED'", priority = 1))
  public void onOrderCompleted(CloudEvent<OrderCompletedEvent> event) {

    log.info("Received ORDER COMPLETED event : {}", event);
    inventoryService.confirmInventory(event.getData().getOrderId());
  }

  @POST
  @Path("/cancelled")
  @Topic(
      name = DaprConfig.ORDER_TOPIC,
      pubsubName = DaprConfig.PUBSUB_NAME,
      rule = @Rule(match = "event.data.type == 'ORDER_CANCELLED'", priority = 2))
  public void onOrderCancelled(CloudEvent<OrderCanceledEvent> event) {

    log.info("Received ORDER CANCELLED event : {}", event);
    // TODO: Implement inventory cancellation logic - release reserved inventory
  }
}
