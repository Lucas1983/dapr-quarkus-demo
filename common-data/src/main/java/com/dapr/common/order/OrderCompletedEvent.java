package com.dapr.common.order;

import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderCompletedEvent(UUID orderId) {}
