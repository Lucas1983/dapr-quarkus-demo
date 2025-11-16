package com.dapr.shipping.business.repository;

import com.dapr.common.DaprConfig;
import com.dapr.shipping.model.entity.Shipment;
import io.quarkiverse.dapr.core.SyncDaprClient;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.UUID;

@Singleton
public class ShippingRepository {

  @Inject SyncDaprClient dapr;

  public List<Shipment> getShipments() {
    return dapr.getBulkState(DaprConfig.STATE_STORE_NAME, List.of(), Shipment.class).stream()
        .map(io.dapr.client.domain.State::getValue)
        .toList();
  }

  public Shipment getShipment(UUID id) {
    return dapr.getState(DaprConfig.STATE_STORE_NAME, String.valueOf(id), Shipment.class)
        .getValue();
  }

  public void saveShipment(Shipment shipment) {
    dapr.saveState(DaprConfig.STATE_STORE_NAME, String.valueOf(shipment.getShipmentId()), shipment);
  }

  public void deleteShipment(UUID id) {
    dapr.deleteState(DaprConfig.STATE_STORE_NAME, String.valueOf(id));
  }
}
