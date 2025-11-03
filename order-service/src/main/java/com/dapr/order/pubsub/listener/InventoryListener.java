package com.dapr.order.pubsub.listener;

import com.dapr.common.DaprConfig;
import com.dapr.common.inventory.InventoryReservationFailedEvent;
import com.dapr.common.inventory.InventoryReservationSucceededEvent;
import com.dapr.order.business.service.OrderService;
import com.dapr.order.model.dictionary.OrderStatus;
import io.dapr.Rule;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Path("/inventory")
public class InventoryListener {

  @Inject OrderService orderService;

  @POST
  @Path("/default")
  @Consumes("application/cloudevents+json")
  public void onUnknown(CloudEvent<Object> event) {
    log.info("Received unknown event : {}", event.getData());
  }

  @POST
  @Path("/reservation/succeeded")
  @Consumes("application/cloudevents+json")
  @Topic(
      name = DaprConfig.INVENTORY_TOPIC,
      pubsubName = DaprConfig.PUBSUB_NAME,
      rule = @Rule(match = "event.data.type == 'INVENTORY_RESERVATION_SUCCEEDED'", priority = 0))
  public void onInventoryReservationSucceed(CloudEvent<InventoryReservationSucceededEvent> event) {

    log.info("Received INVENTORY RESERVATION SUCCEED event : {}", event);
    orderService.updateOrderStatus(event.getData().getOrderId(), OrderStatus.PROCESSING);
  }

  @POST
  @Path("/reservation/failed")
  @Consumes("application/cloudevents+json")
  @Topic(
      name = DaprConfig.INVENTORY_TOPIC,
      pubsubName = DaprConfig.PUBSUB_NAME,
      rule = @Rule(match = "event.data.type == 'INVENTORY_RESERVATION_FAILED'", priority = 1))
  public void onInventoryReservationFailed(CloudEvent<InventoryReservationFailedEvent> event) {

    log.info("Received INVENTORY RESERVATION FAILED event : {}", event);
    // TODO: Implement inventory cancellation logic - release reserved inventory
  }
}
