package com.dapr.order.pubsub.listener;

import com.dapr.common.DaprConfig;
import com.dapr.common.inventory.InventoryReservationFailedEvent;
import com.dapr.common.inventory.InventoryReservationSucceedEvent;
import com.dapr.order.business.service.OrderService;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryListener {

  @Inject OrderService orderService;

  @POST
  @Path("/inventory-reservation-succeeded")
  @Topic(name = DaprConfig.INVENTORY_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderCompleted(CloudEvent<InventoryReservationSucceedEvent> order) {

    log.info(
        "Received INVENTORY RESERVATION SUCCEED event for order {}", order.getData().orderId());
    // TODO: Implement inventory completion logic - update inventory stock
  }

  @POST
  @Path("/inventory-reservation-failed")
  @Topic(name = DaprConfig.INVENTORY_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderCancelled(CloudEvent<InventoryReservationFailedEvent> event) {

    log.info("Received INVENTORY RESERVATION FAILED event for order{}", event.getData().orderId());
    // TODO: Implement inventory cancellation logic - release reserved inventory
  }
}
