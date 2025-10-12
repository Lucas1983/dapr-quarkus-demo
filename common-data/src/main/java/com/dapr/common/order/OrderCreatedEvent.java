package com.dapr.common.order;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderCreatedEvent(UUID orderId, Map<UUID, Integer> products) {}
