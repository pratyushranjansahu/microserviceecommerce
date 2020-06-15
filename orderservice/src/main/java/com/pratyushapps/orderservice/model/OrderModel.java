package com.pratyushapps.orderservice.model;

import java.util.List;

import com.pratyushapps.orderservice.data.OrderLineEntity;

public class OrderModel {
	private String customerId;

	private List<OrderLineEntity> orderLine;

	


	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<OrderLineEntity> getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(List<OrderLineEntity> orderLine) {
		this.orderLine = orderLine;
	}

}
