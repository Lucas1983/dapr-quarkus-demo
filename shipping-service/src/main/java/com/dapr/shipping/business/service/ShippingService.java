package com.dapr.shipping.business.service;

import com.dapr.shipping.business.repository.ShippingRepository;
import com.dapr.shipping.model.Shipment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
@Singleton
public class ShippingService {
    @Inject
    ShippingRepository shippingRepository;

    public List<Shipment> getShipments() {
        return shippingRepository.getShipments();
    }

    public Shipment getShipment(UUID id) {
        return shippingRepository.getShipment(id);
    }

    public void createShipment(Shipment shipment) {
        shippingRepository.createShipment(shipment);
		log.info("Created shipment {}", shipment);
    }

    public void deleteShipment(UUID id) {
        shippingRepository.deleteShipment(id);
    }
}
