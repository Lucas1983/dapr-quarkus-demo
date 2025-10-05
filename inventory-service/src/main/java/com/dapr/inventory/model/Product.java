package com.dapr.inventory.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class Product {

  private UUID id;
  private String name;
  private String description;
  private int quantity;
}
