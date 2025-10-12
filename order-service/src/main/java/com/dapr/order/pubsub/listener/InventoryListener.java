package com.dapr.order.pubsub.listener;

import com.dapr.common.DaprConfig;
import com.dapr.common.inventory.InventoryReservationFailedEvent;
import com.dapr.common.inventory.InventoryReservationSucceededEvent;
import com.dapr.order.business.service.OrderService;
import com.dapr.order.model.dictionary.OrderStatus;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/inventory")
public class InventoryListener {

  @Inject OrderService orderService;

  @POST
  @Path("/reservation/succeeded")
  @Topic(name = DaprConfig.INVENTORY_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onInventoryReservationSucceed(CloudEvent<InventoryReservationSucceededEvent> order) {

    log.info(
        "Received INVENTORY RESERVATION SUCCEED event for order {}", order.getData().orderId());
    orderService.updateOrderStatus(order.getData().orderId(), OrderStatus.PROCESSING);
  }

  @POST
  @Path("/reservation/failed")
  @Topic(name = DaprConfig.INVENTORY_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onInventoryReservationFailed(CloudEvent<InventoryReservationFailedEvent> event) {

    log.info("Received INVENTORY RESERVATION FAILED event for order{}", event.getData().orderId());
    // TODO: Implement inventory cancellation logic - release reserved inventory
  }
}
