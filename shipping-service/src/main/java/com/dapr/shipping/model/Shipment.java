package com.dapr.shipping.model;

import lombok.Data;

@Data
public class Shipment {

	private String shipmentId;
	private String orderId;
	private Status status;
}
