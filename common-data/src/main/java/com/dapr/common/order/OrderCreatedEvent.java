package com.dapr.common.order;

import com.dapr.common.BaseEvent;
import com.dapr.common.EventType;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderCreatedEvent(UUID orderId, Map<UUID, Integer> products) implements BaseEvent {
  @Override
  public EventType type() {
    return EventType.ORDER_CREATED;
  }
}
