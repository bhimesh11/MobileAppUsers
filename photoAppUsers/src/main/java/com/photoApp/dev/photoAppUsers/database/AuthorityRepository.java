package com.photoApp.dev.photoAppUsers.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<authorityEntity , Long>
{
authorityEntity findByName(String name);

}
