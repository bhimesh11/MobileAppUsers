package com.photoApp.dev.photoAppUsers.common;

import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class feignErrorDecoder implements ErrorDecoder
{

    @Autowired
    Environment environment;



    @Override
    public Exception decode(String methodKey, Response response) {

        switch (response.status()) {
            case 400:
                // Do something
                // return new BadRequestException();
                break;
            case 404: {
                if (methodKey.contains("getAlbums")) {
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), environment.getProperty("albums.exceptions.albums-not-found"));
                }
                break;
            }
            default:
                return new Exception(response.reason());
        }
        return null;
    }

    }
