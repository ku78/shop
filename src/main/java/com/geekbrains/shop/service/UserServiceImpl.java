package com.geekbrains.shop.service;
import com.geekbrains.shop.dao.UserRepository;
import com.geekbrains.shop.entities.Role;
import com.geekbrains.shop.entities.User;
import com.geekbrains.shop.dto.UserDto;
import com.geekbrains.shop.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper mapper = UserMapper.MAPPER;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if (user == null) {throw new UsernameNotFoundException("User not found: " + username);}
              
        List<GrantedAuthority> role = new ArrayList<>();
       
        role.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles); 
    }
    
    @Override
    public boolean save(UserDto userDto) {
        if (!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())) {
            throw new RuntimeException("Password is not equal");
        }
        User user = mapper.toUser(userDto);
        user.setRole(Role.CLIENT);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        save(user);
        return true;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public User findByName(String name) {
        return userRepository.findFirstByName(name);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findOneById(id);
    }

    @Override
    public void updateProfile(UserDto dto) {

    }

    private UserDto toDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

}
