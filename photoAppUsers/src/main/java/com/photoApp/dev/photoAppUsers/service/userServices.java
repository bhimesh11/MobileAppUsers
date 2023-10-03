package com.photoApp.dev.photoAppUsers.service;

import com.photoApp.dev.photoAppUsers.common.userDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

public interface userServices extends UserDetailsService
{
    userDto createUser(userDto userDetails);
    userDto getUserDetailsByEmail(String email);
}
