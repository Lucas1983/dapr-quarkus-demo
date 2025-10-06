package com.dapr.shipping.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Shipment {

	private UUID shipmentId;
	private UUID orderId;
	private ShipmentStatus shipmentStatus;
}
