package com.dapr.inventory.model.entity;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

  private UUID id;
  private String name;
  private String description;
  private int quantity;
}
