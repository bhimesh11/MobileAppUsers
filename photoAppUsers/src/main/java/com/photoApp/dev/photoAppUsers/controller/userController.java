package com.photoApp.dev.photoAppUsers.controller;


import com.photoApp.dev.photoAppUsers.common.userDto;
import com.photoApp.dev.photoAppUsers.model.UserResponseModel;
import com.photoApp.dev.photoAppUsers.model.createUserResponseModel;
import com.photoApp.dev.photoAppUsers.model.userRequestModel;
import com.photoApp.dev.photoAppUsers.service.userServices;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

@RestController
@RequestMapping("/users")
public class userController
{

Logger logger = LoggerFactory.getLogger(userController.class);
    @Autowired
    public userServices userServices;

    @Autowired
    public Environment environment;

    @Autowired



    @GetMapping("/check/status")
    public String status()
    {
        return "Working" + " " + environment.getProperty("local.server.port");
    }

    @GetMapping("/check/auth")
    public String authCheck()
    {
        return "Token Verified";
    }

    @GetMapping("/verify/token")
    public String verifyTokenSecretKey()
    {
        return "Token value " + environment.getProperty("token.secret");
    }

    @PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<createUserResponseModel> createUser(@RequestBody @Valid  userRequestModel userReq)
    {
        ModelMapper modelMapper= new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        userDto user = modelMapper.map(userReq,userDto.class);
        logger.debug("User Dto converted" + user);

      userDto createdUser =    userServices.createUser(user);
        logger.debug("user created from the service" + createdUser);

        createUserResponseModel  response = modelMapper.map(createdUser,createUserResponseModel.class);
      logger.debug("Response" + response);
       return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{userId}",
            produces =
                    {MediaType.APPLICATION_JSON_VALUE,
    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId)
    {
        userDto userdto = userServices.getUserByUserId(userId);
        UserResponseModel returnValue = new ModelMapper().map(userdto, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);

    }

}
