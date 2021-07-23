package com.training.springbootusecase.controllers;

import static org.springframework.http.HttpStatus.CREATED;

import com.training.springbootusecase.dto.LoginDto;
import com.training.springbootusecase.dto.RegisterResponseDto;
import com.training.springbootusecase.dto.RegisterUserDto;
import com.training.springbootusecase.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bus-booking-service/user")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> registerUser(RegisterUserDto registerUserDto){
        log.info("Register user - in progress");
        return new ResponseEntity<>(userService.registerUser(registerUserDto), CREATED);
    }
    @PostMapping("/{login}")
    public ResponseEntity<String> login(@RequestBody LoginDto dto){
        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }
}
