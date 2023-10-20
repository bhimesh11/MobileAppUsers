package com.dev.photoAppAPi.PhotoAppAlbumsAPI.io.controller;


import com.dev.photoAppAPi.PhotoAppAlbumsAPI.UI.model.AlbumResponseModel;
import com.dev.photoAppAPi.PhotoAppAlbumsAPI.data.AlbumEntity;
import com.dev.photoAppAPi.PhotoAppAlbumsAPI.service.AlbumService;
import java.lang.reflect.Type;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users/{id}/albums")
public class AlbumsController {

    @Autowired
            AlbumService albumService;


    Logger logger = LoggerFactory.getLogger(this.getClass());




    @GetMapping(
            produces = {
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            })
    public List<AlbumResponseModel> userAlbums(@PathVariable String id)
    {
        List<AlbumResponseModel> returnValue = new ArrayList<>();
        List<AlbumEntity> albumEntityList = albumService.getAlbums(id);

        if(albumEntityList == null || albumEntityList.isEmpty())
        {
            return returnValue;
        }

        Type listType = new TypeToken<List<AlbumResponseModel>>(){}.getType();

        returnValue = new ModelMapper().map(albumEntityList,listType);
        logger.info("Returning " + returnValue.size() + " albums");
        return returnValue;
    }


}
