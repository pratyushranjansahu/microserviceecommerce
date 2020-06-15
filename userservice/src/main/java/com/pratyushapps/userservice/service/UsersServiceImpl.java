package com.pratyushapps.userservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.pratyushapps.userservice.data.AlbumsServiceClient;
import com.pratyushapps.userservice.data.OrderServiceClient;
import com.pratyushapps.userservice.data.UserEntity;
import com.pratyushapps.userservice.data.UsersRepository;
import com.pratyushapps.userservice.model.AlbumResponseModel;
import com.pratyushapps.userservice.model.OrderModel;
import com.pratyushapps.userservice.shared.UserDto;

@Service
public class UsersServiceImpl implements UsersService {

	UsersRepository usersRepository;

	BCryptPasswordEncoder bCryptPasswordEncoder;
	// RestTemplate restTemplate;
	Environment environment;

	AlbumsServiceClient albumsServiceClient;

	OrderServiceClient orderServiceClient;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
			AlbumsServiceClient albumsServiceClient, Environment environment, OrderServiceClient orderServiceClient) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.usersRepository = usersRepository;
		this.albumsServiceClient = albumsServiceClient;
		this.environment = environment;
		this.orderServiceClient = orderServiceClient;
	}

	@Override
	public UserDto createUser(UserDto userDetails) {
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));

		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
		usersRepository.save(userEntity);

		UserDto returnValue = modelMapper.map(userEntity, UserDto.class);

		return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = usersRepository.findByEmail(username);

		if (userEntity == null)
			throw new UsernameNotFoundException(username);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true,
				new ArrayList<>());

	}

	@Override
	public UserDto getUserDetailsByEmail(String email) {
		UserEntity userEntity = usersRepository.findByEmail(email);

		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new ModelMapper().map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = usersRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException("User not found");

		UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

		/*
		 * String albumsUrl = String.format(environment.getProperty("albums.url"),
		 * userId);
		 * 
		 * ResponseEntity<List<AlbumResponseModel>> albumsListResponse =
		 * restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new
		 * ParameterizedTypeReference<List<AlbumResponseModel>>() { });
		 * List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
		 * userDto.setAlbums(albumsList);
		 */

		logger.info("Before calling albums Microservice");
		List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
		logger.info("After calling albums Microservice");

		userDto.setAlbums(albumsList);

		return userDto;
	}
	@Override
	public String postOrder(OrderModel orderModel) {

		logger.info("Before calling order-ws Microservice");

		String str = orderServiceClient.post(orderModel);
		logger.info("After calling order-ws Microservice");

		return str;
	}
}
