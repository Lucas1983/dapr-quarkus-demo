package com.dapr.common.inventory;

import java.util.UUID;
import lombok.Builder;

@Builder
public record InventoryReservationSucceedEvent(UUID orderId) {}
