package com.pratyushapps.userservice.model;

import java.util.List;

public class OrderModel {
	private String customerId;

	private List<OrderLineModel> orderLine;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<OrderLineModel> getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(List<OrderLineModel> orderLine) {
		this.orderLine = orderLine;
	}

}
