package com.dapr.shipping.web.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateShipmentDto {

  private UUID orderId;
}
