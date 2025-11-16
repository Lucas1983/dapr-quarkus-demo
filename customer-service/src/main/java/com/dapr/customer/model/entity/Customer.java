package com.dapr.customer.model.entity;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

  private UUID id;
  private String firstName;
  private String lastName;
  private String address;
  private String email;
}
