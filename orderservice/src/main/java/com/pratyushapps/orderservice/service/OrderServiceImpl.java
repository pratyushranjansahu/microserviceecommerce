package com.pratyushapps.orderservice.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pratyushapps.orderservice.data.CatalogClient;
import com.pratyushapps.orderservice.data.OrderEntity;
import com.pratyushapps.orderservice.data.OrderRepository;
import com.pratyushapps.orderservice.shared.OrderDto;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;

	CatalogClient catalogClient;

	@Autowired
	private OrderServiceImpl(OrderRepository orderRepository, CatalogClient catalogClient) {
		super();
		this.orderRepository = orderRepository;
		this.catalogClient = catalogClient;
	}

	@Override
	public OrderDto order(OrderDto orderdto) {
		if (orderdto.getNumberOfLines() == 0) {
			throw new IllegalArgumentException("No order lines!");
		}
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderEntity orderEntity = modelMapper.map(orderdto, OrderEntity.class);
		orderRepository.save(orderEntity);//orderEntity
		OrderDto returnValue = modelMapper.map(orderEntity, OrderDto.class);
		return returnValue;
	}
	@Override
	public double getPrice(long orderId) {
		
		return orderRepository.findById(orderId).get().totalPrice(catalogClient);
	}

}
