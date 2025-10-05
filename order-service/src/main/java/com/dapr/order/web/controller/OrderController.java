package com.dapr.order.web.controller;

import com.dapr.order.business.service.OrderService;
import com.dapr.order.model.Order;
import jakarta.ws.rs.*;
import java.util.List;
import java.util.UUID;

@Path("order")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GET
  @Path("/")
  public List<Order> getOrders() {
    return orderService.getOrders();
  }

  @GET
  @Path("/{id}")
  public Order getOrder(@PathParam("id") UUID id) {
    return orderService.getOrder(id);
  }

  @POST
  @Path("/")
  public void createOrder(Order order) {
    orderService.createOrder(order);
  }

  @DELETE
  @Path("/{id}")
  public void deleteOrder(@PathParam("id") UUID id) {
    orderService.deleteOrder(id);
  }
}
