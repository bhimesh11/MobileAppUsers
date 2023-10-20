package com.dev.photoAppAPi.PhotoAppAlbumsAPI.service.impl;

import com.dev.photoAppAPi.PhotoAppAlbumsAPI.data.AlbumEntity;
import com.dev.photoAppAPi.PhotoAppAlbumsAPI.service.AlbumService;

import java.util.ArrayList;
import java.util.List;

public class AlbumsServiceImpl implements AlbumService {
    @Override
    public List<AlbumEntity> getAlbums(String userId) {

        List<AlbumEntity> returnValue = new ArrayList<>();

        AlbumEntity albumEntity = new AlbumEntity();
        albumEntity.setUserId(userId);
        albumEntity.setAlbumId("thriller");
        albumEntity.setDescription("Thriller is the sixth studio album by Michael Jackson.");
        albumEntity.setId(1L);
        albumEntity.setName("Thriller");

        AlbumEntity albumEntity2 = new AlbumEntity();
        albumEntity2.setUserId(userId);
        albumEntity2.setAlbumId("abbeyroad");
        albumEntity2.setDescription("Abbey Road is the eleventh studio album by The Beatles.");
        albumEntity2.setId(2L);
        albumEntity2.setName("Abbey Road");

        AlbumEntity albumEntity3 = new AlbumEntity();
        albumEntity3.setUserId(userId);
        albumEntity3.setAlbumId("darksideofthemoon");
        albumEntity3.setDescription("The Dark Side of the Moon is the eighth studio album by Pink Floyd.");
        albumEntity3.setId(3L);
        albumEntity3.setName("The Dark Side of the Moon");

        AlbumEntity albumEntity4 = new AlbumEntity();
        albumEntity4.setUserId(userId);
        albumEntity4.setAlbumId("backinblack");
        albumEntity4.setDescription("Back in Black is the seventh studio album by AC/DC.");
        albumEntity4.setId(4L);
        albumEntity4.setName("Back in Black");

        AlbumEntity albumEntity5 = new AlbumEntity();
        albumEntity5.setUserId(userId);
        albumEntity5.setAlbumId("hotelcalifornia");
        albumEntity5.setDescription("Hotel California is the fifth studio album by the Eagles.");
        albumEntity5.setId(5L);
        albumEntity5.setName("Hotel California");


        returnValue.add(albumEntity);
        returnValue.add(albumEntity2);
        returnValue.add(albumEntity3);
        returnValue.add(albumEntity4);
        returnValue.add(albumEntity5);

        return returnValue;

    }
}
