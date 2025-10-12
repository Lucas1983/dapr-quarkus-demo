package com.dapr.common;

public enum EventType {
  ORDER_COMPLETED("order-completed"),
  ORDER_CANCELLED("order-cancelled"),
  ORDER_CREATED("order-created"),
  INVENTORY_RESERVATION_SUCCEEDED("inventory-reservation-succeeded"),
  INVENTORY_RESERVATION_FAILED("inventory-reservation-failed");

  private String type;

  EventType(String type) {
    this.type = type;
  }
}
