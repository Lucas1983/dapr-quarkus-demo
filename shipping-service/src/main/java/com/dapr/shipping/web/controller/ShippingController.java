package com.dapr.shipping.web.controller;

import com.dapr.shipping.business.service.ShippingService;
import com.dapr.shipping.model.entity.Shipment;
import com.dapr.shipping.model.dictionary.ShipmentStatus;
import com.dapr.shipping.web.dto.CreateShipmentDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import java.util.List;
import java.util.UUID;

@Path("/shipping")
public class ShippingController {
  @Inject ShippingService shippingService;

  @GET
  @Path("/shipments")
  public List<Shipment> getShipments() {
    return shippingService.getShipments();
  }

  @GET
  @Path("/shipments/{id}")
  public Shipment getShipment(@PathParam("id") UUID id) {
    return shippingService.getShipment(id);
  }

  @POST
  @Path("/shipments")
  public void createShipment(CreateShipmentDto dto) {
    shippingService.createShipment(dto.getOrderId());
  }

  @PUT
  @Path("/{id}/status")
  public void updateShipmentStatus(
      @PathParam("id") UUID id, @QueryParam("status") ShipmentStatus status) {
    shippingService.updateShipmentStatus(id, status);
  }

  @DELETE
  @Path("/shipments/{id}")
  public void deleteShipment(@PathParam("id") UUID id) {
    shippingService.deleteShipment(id);
  }
}
