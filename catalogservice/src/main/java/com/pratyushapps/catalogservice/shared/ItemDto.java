package com.pratyushapps.catalogservice.shared;

import javax.persistence.Column;

public class ItemDto {
	
	private Long id;
	private String name;
	private double price;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ItemDto [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	
}
