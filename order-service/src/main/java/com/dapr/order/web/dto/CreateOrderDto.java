package com.dapr.order.web.dto;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderDto {

  private UUID customerId;
  private Map<UUID, Integer> products;
}
