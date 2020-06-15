package com.pratyushapps.albumservice.service;

import java.util.List;

import com.pratyushapps.albumservice.data.AlbumEntity;

public interface AlbumsService {
	 List<AlbumEntity> getAlbums(String userId);
}
