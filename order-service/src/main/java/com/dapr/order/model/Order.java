package com.dapr.order.model;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class Order {

  UUID orderId;
  UUID customerId;
  Map<UUID, Integer> products;
  OrderStatus orderStatus;
  Instant createdAt;
}
