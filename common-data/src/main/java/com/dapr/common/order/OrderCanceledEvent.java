package com.dapr.common.order;

import java.util.UUID;
import lombok.Builder;

@Builder
public record OrderCanceledEvent(UUID orderId) {}
