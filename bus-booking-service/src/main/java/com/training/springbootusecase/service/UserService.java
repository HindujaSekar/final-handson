package com.training.springbootusecase.service;

import com.training.springbootusecase.dto.LoginDto;
import com.training.springbootusecase.dto.RegisterResponseDto;
import com.training.springbootusecase.dto.RegisterUserDto;
import com.training.springbootusecase.entity.User;
import com.training.springbootusecase.exceptions.AuthenticationException;
import com.training.springbootusecase.exceptions.DuplicateUserException;
import com.training.springbootusecase.exceptions.NoSuchUserException;
import com.training.springbootusecase.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service(value = "userService")
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = repository.findByEmail(email);
        if(user == null){
            log.info("Login failed");
            throw new NoSuchUserException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    public RegisterResponseDto registerUser(RegisterUserDto registerUserDto){
        if (repository.existsUserByEmail(registerUserDto.getEmail()))
            throw new DuplicateUserException("User already registered");
        User user = User.builder()
                .firstName(registerUserDto.getFirstName())
                .lastName(registerUserDto.getLastName())
                .email(registerUserDto.getEmail())
                .gender(registerUserDto.getGender())
                .dateOfBirth(LocalDate.parse(registerUserDto.getDateOfBirth())).build();
        user.setPassword(bcryptEncoder.encode(registerUserDto.getPassword()));
        repository.save(user);
        return RegisterResponseDto.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .message("Hello! "+user.getFirstName()+" You are successfully registered! ").build();
    }
    public User getUserByEmail(String email){
        if (repository.existsUserByEmail(email))
            return repository.findByEmail(email);
        else {
            log.info("User doesn't exist");
            throw new NoSuchUserException("User cannot be found");
        }
    }
    public String login(LoginDto dto) {
        User user = getUserByEmail(dto.getEmail());
        if (user.getPassword().equals(dto.getPassword()))
            return "login successful";
        else {
            log.info("Authentication failed");
            throw new AuthenticationException("Password is wrong");
        }
    }
}
