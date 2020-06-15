package com.pratyushapps.catalogservice.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.pratyushapps.catalogservice.data.ItemRepository;
import com.pratyushapps.catalogservice.model.ItemModel;
import com.pratyushapps.catalogservice.service.ItemService;
import com.pratyushapps.catalogservice.shared.ItemDto;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

	private final ItemRepository itemRepository;

	private ItemService itemService;

	@Autowired
	public CatalogController(ItemRepository itemRepository, ItemService itemService) {
		this.itemService = itemService;
		this.itemRepository = itemRepository;
	}

	@PostMapping
	public String post(@RequestBody ItemModel itemModel) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		ItemDto itemDto = modelMapper.map(itemModel, ItemDto.class);
		ItemDto createdUser = itemService.saveItem(itemDto);

		return "Item Added";
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<ItemModel> findAll() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ItemDto> itemList = itemService.getItems();
		List<ItemModel> itemModelList = Arrays.asList(modelMapper.map(itemList, ItemModel[].class));
		return itemModelList;
	}
	
	
	@GetMapping(value="/{itemId}")	
	public double price(@PathVariable long itemId) {
		//Long id=Long.parseLong(itemId);
		return itemService.getItemById(itemId).getPrice();
	}
}
