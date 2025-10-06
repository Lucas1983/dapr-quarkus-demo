package com.dapr.order.web.controller.dto;

import java.util.Map;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateOrderDto {

  UUID customerId;
  Map<UUID, Integer> products;
}
