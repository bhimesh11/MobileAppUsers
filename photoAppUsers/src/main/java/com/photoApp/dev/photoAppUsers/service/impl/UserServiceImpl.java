package com.photoApp.dev.photoAppUsers.service.impl;

import com.photoApp.dev.photoAppUsers.common.userDto;
import com.photoApp.dev.photoAppUsers.database.UserEntity;
import com.photoApp.dev.photoAppUsers.model.AlbumResponseModel;
import com.photoApp.dev.photoAppUsers.repository.AlbumsServiceClient;
import com.photoApp.dev.photoAppUsers.service.userServices;
import org.modelmapper.ModelMapper;
import com.photoApp.dev.photoAppUsers.repository.usersRepo;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class UserServiceImpl implements userServices
{
    @Autowired
   public   usersRepo usersRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    //RestTemplate restTemplate;
    AlbumsServiceClient albumsServiceClient;

    Logger logger = LoggerFactory.getLogger("UserServiceImpl.class");


    @Autowired
    Environment environment;

    public UserServiceImpl()
    {

    }

    @Override
    public userDto createUser(userDto userDetails) {

      userDetails.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
       UserEntity entity = modelMapper.map(userDetails, UserEntity.class);
       logger.debug("entity " + entity);

      // entity.setEncryptedPassword(UUID.randomUUID().toString());
        entity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        System.out.println("password encrypted");
        usersRepo.save(entity);
      userDto user =  modelMapper.map(entity, userDto.class);

      logger.debug("converted back to user" + user);

      return  user;

    }

    @Override
    public userDto getUserDetailsByEmail(String email) {

        ModelMapper model = new ModelMapper();
     UserEntity user = usersRepo.findByEmail(email);
        System.out.println(user);
     if(user==null)
     {
         throw new UsernameNotFoundException(user.getEmail());
     }
        userDto dto =  model.map(user,userDto.class);
        System.out.println(dto.toString());
     return dto;

    }

    @Override
    public userDto getUserByUserId(String userId) {

        UserEntity user = usersRepo.findByUserId(userId);
        if(user == null ) throw new UsernameNotFoundException("User not found");

        userDto userdto = new ModelMapper().map(user,userDto.class);

//        String albumsURI = String.format(environment.getProperty("albums-url"),userId);
//        ResponseEntity<List<AlbumResponseModel>> albumResponseModel = restTemplate.exchange(albumsURI, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>(){
//        });
//        List<AlbumResponseModel> albumList = albumResponseModel.getBody();

        logger.debug("Before calling" + userdto);
        List<AlbumResponseModel> albumlist = albumsServiceClient.getAlbums(userId);
        logger.debug("After calling " + albumlist);

        userdto.setAlbums(albumlist);
        return userdto;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity user=    usersRepo.findByEmail(username);

        System.out.println(user.toString());

        if(user==null) {
            throw new UsernameNotFoundException(username);
        }
        System.out.println(user.toString());
            return new User(user.getEmail(),user.getEncryptedPassword(),true,true,true,true,new ArrayList<>());

       // throw  new UsernameNotFoundException(username);
    }
}
