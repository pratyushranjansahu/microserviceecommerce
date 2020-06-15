package com.pratyushapps.orderservice.data;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pratyushapps.orderservice.model.ItemModel;

import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "catalog-ws", fallbackFactory = CatalogFallbackFactory.class)

public interface CatalogClient {
	@GetMapping("/catalog/{itemId}")
	public double price(@PathVariable long itemId);
	@GetMapping("/catalog")
	public Collection<ItemModel> findAll();

}

@Component
class CatalogFallbackFactory implements FallbackFactory<CatalogClient> {

	@Override
	public CatalogClient create(Throwable cause) {

		return new CatalogClientFallback(cause);
	}

	class CatalogClientFallback implements CatalogClient {

		Logger logger = LoggerFactory.getLogger(this.getClass());

		private final Throwable cause;

		public CatalogClientFallback(Throwable cause) {
			this.cause = cause;
		}

		@Override
		public double price(long itemId) {

			if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
				logger.error("404 error took place when getAlbums was called with , Error message: "
						+ cause.getLocalizedMessage());
			} else {
				logger.error("Other error took place: " + cause.getLocalizedMessage());
			}

			return new Double(0);
		}

		@Override
		public Collection<ItemModel> findAll() {

			return new ArrayList<ItemModel>();
		}
	}
}
