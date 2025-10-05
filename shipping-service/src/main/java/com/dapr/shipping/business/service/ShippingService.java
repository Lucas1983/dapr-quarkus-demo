package com.dapr.shipping.business.service;

import com.dapr.shipping.business.repository.ShippingRepository;
import com.dapr.shipping.model.Shipment;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class ShippingService {
    @Inject
    ShippingRepository shippingRepository;

    public List<Shipment> getShipments() {
        return shippingRepository.getShipments();
    }

    public Shipment getShipment(int id) {
        return shippingRepository.getShipment(id);
    }

    public void createShipment(Shipment shipment) {
        shippingRepository.createShipment(shipment);
    }

    public void deleteShipment(int id) {
        shippingRepository.deleteShipment(id);
    }
}
