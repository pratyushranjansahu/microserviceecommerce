package com.pratyushapps.catalogservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pratyushapps.catalogservice.data.ItemEntity;
import com.pratyushapps.catalogservice.data.ItemRepository;
import com.pratyushapps.catalogservice.shared.ItemDto;
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemRepository itemRepository;
	

	@Override
	public ItemDto saveItem(ItemDto itemDto) {
	
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ItemEntity itemEntity = modelMapper.map(itemDto, ItemEntity.class);
		itemRepository.save(itemEntity);

		ItemDto returnValue = modelMapper.map(itemEntity, ItemDto.class);

		return returnValue;
	
	}

	@Override
	public List<ItemDto> getItems() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		List<ItemEntity> itemEntityList=(List<ItemEntity>) itemRepository.findAll();
		List<ItemDto> itemDtoList = Arrays.asList(modelMapper.map(itemEntityList, ItemDto[].class));
		return itemDtoList;
	}

	@Override
	public ItemDto getItemById(long itemId) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		Optional<ItemEntity> optionalItem=itemRepository.findById(itemId);
		ItemEntity itemEntity=optionalItem.get();
		ItemDto itemDto=modelMapper.map(itemEntity, ItemDto.class);
		
		return itemDto;
	}

}
