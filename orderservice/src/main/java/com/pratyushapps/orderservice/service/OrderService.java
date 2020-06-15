package com.pratyushapps.orderservice.service;

import com.pratyushapps.orderservice.shared.OrderDto;

public interface OrderService {
	OrderDto order(OrderDto orderdto);

	public double getPrice(long orderId);
}
