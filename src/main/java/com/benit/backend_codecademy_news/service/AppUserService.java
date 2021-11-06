package com.benit.backend_codecademy_news.service;

import com.benit.backend_codecademy_news.dto.user.SignUpDto;
import com.benit.backend_codecademy_news.dto.user.UpdateUserDto;
import com.benit.backend_codecademy_news.entity.AppUser;
import com.benit.backend_codecademy_news.exception.ResourceNotFoundException;

import java.util.Collection;

public interface AppUserService {
    AppUser saveUser(SignUpDto signUpDto);
    AppUser deleteUser(Long id);
    AppUser updateUser(Long id, UpdateUserDto updateUserDto) throws ResourceNotFoundException;
    AppUser getUserByUsername(String username);
    AppUser getUserById(Long id);
    Collection<AppUser> getUsers();
}
