package com.dev.photoAppAPi.PhotoAppAlbumsAPI.service;

import com.dev.photoAppAPi.PhotoAppAlbumsAPI.data.AlbumEntity;

import java.util.List;

public interface AlbumService {

    List<AlbumEntity> getAlbums(String userId);



}
