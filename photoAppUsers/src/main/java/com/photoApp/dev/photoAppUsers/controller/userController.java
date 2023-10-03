package com.photoApp.dev.photoAppUsers.controller;


import com.photoApp.dev.photoAppUsers.common.userDto;
import com.photoApp.dev.photoAppUsers.model.createUserResponseModel;
import com.photoApp.dev.photoAppUsers.model.userRequestModel;
import com.photoApp.dev.photoAppUsers.service.userServices;
import freemarker.core.Environment;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class userController
{


    public userServices userServices;

    public userController(com.photoApp.dev.photoAppUsers.service.userServices userServices) {
        this.userServices = userServices;
    }

    @Value("${server.port}")
    private int port;
    @GetMapping("/status/check")
    public String status()
    {
        return "Working" + " " + port;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<createUserResponseModel> createUser(@RequestBody @Valid  userRequestModel userReq)
    {
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        userDto user = modelMapper.map(userReq,userDto.class);
      userDto createdUser =    userServices.createUser(user);
        createUserResponseModel  response = modelMapper.map(createdUser,createUserResponseModel.class);
       return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
