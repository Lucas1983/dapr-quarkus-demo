package com.dapr.shipping.business.repository;

import com.dapr.shipping.model.Shipment;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class ShippingRepository {
    private final List<Shipment> shipments = new ArrayList<>();

    public List<Shipment> getShipments() {
        return List.copyOf(shipments);
    }

    public Shipment getShipment(int id) {
        return shipments.stream().filter(s -> s.hashCode() == id).findFirst().orElse(null);
    }

    public void createShipment(Shipment shipment) {
        shipments.add(shipment);
    }

    public void deleteShipment(int id) {
        shipments.removeIf(s -> s.hashCode() == id);
    }
}
