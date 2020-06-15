package com.pratyushapps.orderservice.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String customerId;

	@OneToMany(cascade = CascadeType.ALL)
	private List<OrderLineEntity> orderLine;

	public OrderEntity() {
		super();
		orderLine = new ArrayList<OrderLineEntity>();
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<OrderLineEntity> getOrderLine() {
		return orderLine;
	}

	public OrderEntity(String customerId) {
		super();
		this.customerId = customerId;
		this.orderLine = new ArrayList<OrderLineEntity>();
	}

	public void setOrderLine(List<OrderLineEntity> orderLine) {
		this.orderLine = orderLine;
	}

	public void addLine(int count, long itemId) {
		this.orderLine.add(new OrderLineEntity(count, itemId));
	}

	public int getNumberOfLines() {
		return orderLine.size();
	}

	public double totalPrice(CatalogClient itemClient) {
		return orderLine.stream()
				.map((ol) -> ol.getCount() * itemClient.price(ol.getItemId()))
				.reduce(0.0, (d1, d2) -> d1 + d2);
	}

	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);

	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
