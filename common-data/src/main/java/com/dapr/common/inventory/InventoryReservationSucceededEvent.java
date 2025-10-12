package com.dapr.common.inventory;

import com.dapr.common.BaseEvent;
import com.dapr.common.EventType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record InventoryReservationSucceededEvent(UUID orderId) implements BaseEvent {
  @Override
  public EventType type() {
    return EventType.INVENTORY_RESERVATION_SUCCEEDED;
  }
}
