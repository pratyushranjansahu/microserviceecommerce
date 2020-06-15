package com.pratyushapps.catalogservice;

import javax.annotation.PostConstruct;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.pratyushapps.catalogservice.data.ItemEntity;
import com.pratyushapps.catalogservice.data.ItemRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class CatalogserviceApplication {

	private final ItemRepository itemRepository;

	@Autowired
	public CatalogserviceApplication(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CatalogserviceApplication.class, args);
	}

	@PostConstruct
	public void generateTestData() {
		itemRepository.save(new ItemEntity("iPod", 42.0));
		itemRepository.save(new ItemEntity("iPod touch", 21.0));
		itemRepository.save(new ItemEntity("iPod nano", 1.0));
		itemRepository.save(new ItemEntity("Apple TV", 100.0));
		
	}

}
