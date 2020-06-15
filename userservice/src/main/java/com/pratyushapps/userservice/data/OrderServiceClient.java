package com.pratyushapps.userservice.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pratyushapps.userservice.model.OrderModel;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "order-ws", fallbackFactory = OrderFallbackFactory.class)
public interface OrderServiceClient {
	@PostMapping(("/order"))
	public String post(@RequestBody OrderModel orderModel);
}

@Component
class OrderFallbackFactory implements FallbackFactory<OrderServiceClient> {

	@Override
	public OrderServiceClient create(Throwable cause) {

		return new OrderServiceClientFallback(cause);
	}

	class OrderServiceClientFallback implements OrderServiceClient {

		Logger logger = LoggerFactory.getLogger(this.getClass());

		private final Throwable cause;

		public OrderServiceClientFallback(Throwable cause) {
			this.cause = cause;
		}

		@Override
		public String post(OrderModel orderModel) {
			if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
				logger.error("404 error took place when getAlbums was called with userId Error message: "
						+ cause.getLocalizedMessage());
			} else {
				logger.error("Other error took place: " + cause.getLocalizedMessage());
			}
			return "Unable to fetch data";
		}

	}
}
