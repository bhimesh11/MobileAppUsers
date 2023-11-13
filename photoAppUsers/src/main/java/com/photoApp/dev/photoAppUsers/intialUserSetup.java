package com.photoApp.dev.photoAppUsers;

import com.netflix.discovery.converters.Auto;
import com.photoApp.dev.photoAppUsers.common.Roles;
import com.photoApp.dev.photoAppUsers.database.*;
import com.photoApp.dev.photoAppUsers.repository.usersRepo;
import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Component
public class intialUserSetup
{

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    usersRepo usersRepo;





    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Transactional
    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event)
    {
        logger.info("From Application Ready Event");

        authorityEntity readAuthority = createAuthority("READ");
        authorityEntity writeAuthority = createAuthority("WRITE");
        authorityEntity deleteAuthority = createAuthority("DELETE");

        roleEntity USER_ROLE = createRole(String.valueOf(Roles.ROLE_USER), Arrays.asList(readAuthority,writeAuthority));
     roleEntity ADMIN_ROLE =   createRole(String.valueOf(Roles.ROLE_ADMIN),Arrays.asList(readAuthority,writeAuthority,deleteAuthority));

     logger.info("Admin Role " +ADMIN_ROLE.toString());

     if(ADMIN_ROLE != null)
     {
         UserEntity adminUser = new UserEntity();
         adminUser.setFirstName("Sergey");
         adminUser.setLastName("Kargopolov");
         adminUser.setEmail("admin@test.com");
         adminUser.setUserId(UUID.randomUUID().toString());
         adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("12345678"));
         adminUser.setRoles(Arrays.asList(ADMIN_ROLE));

         logger.info("set the properties");

         UserEntity userAlreadyExists = usersRepo.findByEmail("admin@test.com");

         if(userAlreadyExists == null)
         {
             usersRepo.save(adminUser);
         }
     }
     else {
         logger.info(ADMIN_ROLE.toString());
     }
    }
    @Transactional
    public authorityEntity createAuthority(String name)
    {

        authorityEntity authEntity = authorityRepository.findByName(name);

        if(authEntity == null)
        {
            authEntity = new authorityEntity(name);
            authorityRepository.save(authEntity);

        }
        return authEntity;
    }
private roleEntity createRole(String name, Collection<authorityEntity> authorties)
{

    roleEntity roleEntity = roleRepository.findByName(name);

    if(roleEntity == null)
    {
        roleEntity = new roleEntity(name,authorties);
        roleRepository.save(roleEntity);
    }

    return roleEntity;

}
}
