package com.pratyushapps.orderservice.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratyushapps.orderservice.data.CatalogClient;
import com.pratyushapps.orderservice.data.OrderRepository;
import com.pratyushapps.orderservice.model.OrderModel;
import com.pratyushapps.orderservice.service.OrderService;
import com.pratyushapps.orderservice.shared.OrderDto;

@RestController
@RequestMapping("/order")
public class OrderController {
	private OrderRepository orderRepository;

	private OrderService orderService;

	private CatalogClient catalogClient;
	
	private Environment environment;
	
	@Autowired
	private OrderController(OrderService orderService,
			OrderRepository orderRepository,
			CatalogClient catalogClient,Environment environment) {
		super();
		this.orderRepository = orderRepository;
		
		this.catalogClient = catalogClient;
		this.orderService = orderService;
		this.environment=environment;
	}
	@GetMapping("/status/check")
	public String status() {
		return "Working on Port :" + environment.getProperty("local.server.port");
	}
	@PostMapping
	public String post(@RequestBody OrderModel orderModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderDto orderDto = modelMapper.map(orderModel, OrderDto.class);

		
		orderDto = orderService.order(orderDto);
		Double price = orderService.getPrice(orderDto.getId());
		return "You cart value is Rs."+price;
	}
}
