package com.photoApp.dev.photoAppUsers.repository;

import com.photoApp.dev.photoAppUsers.database.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usersRepo extends CrudRepository<UserEntity,Long>
{

    UserEntity findByEmail(String email);


}
