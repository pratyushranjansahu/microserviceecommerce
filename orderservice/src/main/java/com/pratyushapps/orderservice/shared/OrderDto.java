package com.pratyushapps.orderservice.shared;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.pratyushapps.orderservice.data.OrderLineEntity;

public class OrderDto {

	private long id;
	
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	private List<OrderLineEntity> orderLine;

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public List<OrderLineEntity> getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(List<OrderLineEntity> orderLine) {
		this.orderLine = orderLine;
	}
	public int getNumberOfLines() {
		return orderLine.size();
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
