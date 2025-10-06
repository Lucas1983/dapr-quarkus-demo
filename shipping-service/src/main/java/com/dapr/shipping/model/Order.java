package com.dapr.shipping.model;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class Order {

  UUID orderId;
  UUID customerId;
  Map<UUID, Integer> products;
  OrderStatus status;
  Instant createdAt;
}
