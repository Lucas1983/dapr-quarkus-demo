package com.dapr.shipping.model.entity;

import com.dapr.shipping.model.dictionary.ShipmentStatus;
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
