package com.geekbrains.shop.service;

import com.geekbrains.shop.entities.User;
import com.geekbrains.shop.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    boolean save(UserDto userDto);
    void save(User user);
    List<UserDto> getAll();
    User findByName(String name);
    User findById(Long id);
    void updateProfile(UserDto dto);
}