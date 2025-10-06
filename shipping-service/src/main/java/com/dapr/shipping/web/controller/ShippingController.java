package com.dapr.shipping.web.controller;

import com.dapr.shipping.business.service.ShippingService;
import com.dapr.shipping.model.Shipment;
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
  public void createShipment(Shipment shipment) {
    shippingService.createShipment(shipment);
  }

  @DELETE
  @Path("/shipments/{id}")
  public void deleteShipment(@PathParam("id") UUID id) {
    shippingService.deleteShipment(id);
  }
}
