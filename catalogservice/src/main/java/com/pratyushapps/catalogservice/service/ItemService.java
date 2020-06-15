package com.pratyushapps.catalogservice.service;

import java.util.List;

import com.pratyushapps.catalogservice.shared.ItemDto;

public interface ItemService {
	ItemDto saveItem(ItemDto itemDto);

	List<ItemDto> getItems();
	ItemDto getItemById(long itemId);
}
