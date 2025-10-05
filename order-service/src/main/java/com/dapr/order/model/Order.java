package com.dapr.order.model;

import lombok.Data;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Data
public class Order {

  UUID orderId;
  UUID customerId;
  Map<UUID, Integer> products;
  Status status;
  Instant createdAt;
}
