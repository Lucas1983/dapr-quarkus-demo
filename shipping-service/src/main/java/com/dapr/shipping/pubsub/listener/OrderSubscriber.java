package com.dapr.shipping.pubsub.listener;

import com.dapr.common.DaprConfig;
import com.dapr.common.order.OrderCanceledEvent;
import com.dapr.common.order.OrderCompletedEvent;
import com.dapr.shipping.business.service.ShippingService;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderSubscriber {
  @Inject ShippingService shippingService;

  @POST
  @Path("/order-completed")
  @Topic(name = DaprConfig.ORDER_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderCompleted(CloudEvent<OrderCompletedEvent> order) {

    log.info("Received ORDER COMPLETED event{}", order.getData().orderId());
    shippingService.createShipment(order.getData().orderId());
  }

  @POST
  @Path("/order-cancelled")
  @Topic(name = DaprConfig.ORDER_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderCancelled(CloudEvent<OrderCanceledEvent> event) {

    log.info("Received ORDER CANCELLED event {}", event.getData().orderId());
    // TODO: Implement shipment cancellation logic - remove shipment if exists
  }
}
