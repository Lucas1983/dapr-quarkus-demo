package com.dapr.common.order;

import com.dapr.common.BaseEvent;
import com.dapr.common.EventType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderCanceledEvent(UUID orderId) implements BaseEvent {
  @Override
  public EventType type() {
    return EventType.ORDER_CANCELLED;
  }
}
