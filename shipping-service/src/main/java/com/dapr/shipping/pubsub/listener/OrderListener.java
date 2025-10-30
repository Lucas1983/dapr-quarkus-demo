package com.dapr.shipping.pubsub.listener;

import com.dapr.common.order.OrderCanceledEvent;
import com.dapr.common.order.OrderCompletedEvent;
import com.dapr.common.order.OrderCreatedEvent;
import com.dapr.shipping.business.service.ShippingService;
import io.dapr.client.domain.CloudEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Path("/orders")
public class OrderListener {

  @Inject ShippingService shippingService;

  @POST
  @Path("/default")
  public void onUnknown(CloudEvent<Object> event) {
    log.info("Received unknown event : {}", event.getData());
  }

	@POST
	@Path("/created")
	//  @Topic(
	//      name = DaprConfig.ORDER_TOPIC,
	//      pubsubName = DaprConfig.PUBSUB_NAME,
	//      rule = @Rule(match = "event.type == 'order-created'", priority = 0))
	public void onOrderCreated(CloudEvent<OrderCreatedEvent> event) {

		log.info("Received ORDER CREATED event : {}", event);
	}

  @POST
  @Path("/completed")
  //  @Topic(
  //      name = DaprConfig.ORDER_TOPIC,
  //      pubsubName = DaprConfig.PUBSUB_NAME,
  //      rule = @Rule(match = "event.type == 'order-completed'", priority = 0))
  public void onOrderCompleted(CloudEvent<OrderCompletedEvent> event) {

    log.info("Received ORDER COMPLETED event : {}", event);
    shippingService.createShipment(event.getData().getOrderId());
  }

  @POST
  @Path("/cancelled")
  //  @Topic(
  //      name = DaprConfig.ORDER_TOPIC,
  //      pubsubName = DaprConfig.PUBSUB_NAME,
  //      rule = @Rule(match = "event.type == 'order-canceled'", priority = 1))
  public void onOrderCancelled(CloudEvent<OrderCanceledEvent> event) {

    log.info("Received ORDER CANCELLED event {}", event);
    // TODO: Implement shipment cancellation logic - remove shipment if exists
  }
}
