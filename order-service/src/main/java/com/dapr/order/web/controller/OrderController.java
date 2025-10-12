package com.dapr.order.web.controller;

import com.dapr.order.business.service.OrderService;
import com.dapr.order.model.entity.Order;
import com.dapr.order.model.dictionary.OrderStatus;
import com.dapr.order.web.dto.CreateOrderDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.List;
import java.util.UUID;

@Path("order")
public class OrderController {

  @Inject OrderService orderService;

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
  public void createOrder(CreateOrderDto dto) {
    orderService.createOrder(dto);
  }

  @PUT
  @Path("/{id}/status")
  public void updateOrderStatus(@PathParam("id") UUID id, @QueryParam("status") OrderStatus status) {
    orderService.updateOrderStatus(id, status);
  }

  @DELETE
  @Path("/{id}")
  public void removeOrder(@PathParam("id") UUID id) {
    orderService.removeOrder(id);
  }
}
