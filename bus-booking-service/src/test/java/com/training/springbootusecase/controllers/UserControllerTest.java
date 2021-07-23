package com.training.springbootusecase.controllers;

import com.training.springbootusecase.dto.LoginDto;
import com.training.springbootusecase.dto.RegisterResponseDto;
import com.training.springbootusecase.dto.RegisterUserDto;
import com.training.springbootusecase.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    UserService userService;

    @Test
    public void testRegisterUser(){
        when(userService.registerUser(RegisterUserDto.builder().build()))
                .thenReturn(RegisterResponseDto.builder().message("Registered").build());
        RegisterResponseDto response = userController.registerUser(RegisterUserDto.builder().build()).getBody();
        assertNotNull(response);
        assertEquals("Registered",response.getMessage());
    }
    @Test
    public void testLogin(){
        when(userService.login(LoginDto.builder().build()))
                .thenReturn("login successful");
        String response = userController.login(LoginDto.builder().build()).getBody();
        assertNotNull(response);
        assertEquals("login successful",response);
    }

}
