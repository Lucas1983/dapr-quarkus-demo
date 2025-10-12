package com.dapr.shipping.business.service;

import com.dapr.shipping.business.repository.ShippingRepository;
import com.dapr.shipping.model.dictionary.ShipmentStatus;
import com.dapr.shipping.model.entity.Shipment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ShippingService {
  @Inject ShippingRepository shippingRepository;

  public List<Shipment> getShipments() {
    return shippingRepository.getShipments();
  }

  public Shipment getShipment(UUID id) {
    return shippingRepository.getShipment(id);
  }

  public void createShipment(UUID orderId) {

    Shipment shipment =
        Shipment.builder()
            .shipmentId(UUID.randomUUID())
            .orderId(orderId)
            .shipmentStatus(ShipmentStatus.NEW)
            .build();

    shippingRepository.saveShipment(shipment);
    log.info("Created shipment {}", shipment);
  }

  public void updateShipmentStatus(UUID id, ShipmentStatus status) {

    Optional.of(shippingRepository.getShipment(id))
        .ifPresentOrElse(
            shipment -> {
              shipment.setShipmentStatus(status);
              shippingRepository.saveShipment(shipment);
              log.info("Updated shipment {}", shipment);
            },
            () -> {
              throw new RuntimeException("Shipment not found: " + id);
            });
  }

  public void deleteShipment(UUID id) {
    shippingRepository.deleteShipment(id);
  }
}
