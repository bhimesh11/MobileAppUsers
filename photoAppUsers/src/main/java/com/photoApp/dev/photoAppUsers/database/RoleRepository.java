package com.photoApp.dev.photoAppUsers.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<roleEntity , Long> {

   roleEntity findByName(String name);


}
