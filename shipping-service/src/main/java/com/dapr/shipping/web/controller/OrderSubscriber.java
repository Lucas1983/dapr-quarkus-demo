package com.dapr.shipping.web.controller;

import com.dapr.shipping.business.service.ShippingService;
import com.dapr.shipping.config.DaprConfig;
import com.dapr.shipping.model.Order;
import com.dapr.shipping.model.OrderStatus;
import com.dapr.shipping.model.Shipment;
import com.dapr.shipping.model.ShipmentStatus;
import io.dapr.Topic;
import io.dapr.client.domain.CloudEvent;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Path("/orders")
public class OrderSubscriber {
  @Inject ShippingService shippingService;

  @POST
  @Topic(name = DaprConfig.ORDER_TOPIC, pubsubName = DaprConfig.PUBSUB_NAME)
  public void onOrderRecieved(CloudEvent<Order> order) {

    log.info("Received order {}", order.getData());

    if (order.getData().getStatus() == OrderStatus.COMPLETED) {
      log.warn("Order {} is COMPLETED : ", order.getData().getOrderId());
      Shipment shipment =
          Shipment.builder()
              .shipmentId(UUID.randomUUID())
              .orderId(order.getData().getOrderId())
              .shipmentStatus(ShipmentStatus.NEW)
              .build();
      shippingService.createShipment(shipment);
    }
    if (order.getData().getStatus() == (OrderStatus.CANCELLED)) {
      log.info("Order {} is CANCELLED : ", order.getData().getOrderId());
      // TODO: Implement shipment cancellation logic - remove shipment if exists
    }
  }
}
