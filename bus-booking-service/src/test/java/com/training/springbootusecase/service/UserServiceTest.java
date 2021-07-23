package com.training.springbootusecase.service;

import com.training.springbootusecase.dto.LoginDto;
import com.training.springbootusecase.dto.RegisterResponseDto;
import com.training.springbootusecase.dto.RegisterUserDto;
import com.training.springbootusecase.entity.User;
import com.training.springbootusecase.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @Test
    public void testRegisterUser() {
        RegisterUserDto registerUserDto = buildRegisterUserDto();
        when(userRepository.existsUserByEmail(registerUserDto.getEmail())).thenReturn(false);
        when(encoder.encode(registerUserDto.getPassword())).thenReturn(any(String.class));
        RegisterResponseDto response = service.registerUser(buildRegisterUserDto());
        assertNotNull(response);
        assertEquals("email",response.getEmail());
    }
    @Test
    public void testGetUserByEmail(){
        String email = "email";
        when(userRepository.existsUserByEmail(email)).thenReturn(true);
        when(userRepository.findByEmail(email)).thenReturn(User.builder().email(email).build());
        User response = service.getUserByEmail(email);
        assertNotNull(response);
        assertEquals(email,response.getEmail());
    }
    @Test
    public void testLogin(){
        when(userRepository.existsUserByEmail(buildLoginDto().getEmail())).thenReturn(true);
        when(userRepository.findByEmail(buildLoginDto().getEmail())).thenReturn(User.builder().password("password").build());
        String response = service.login(buildLoginDto());
        assertNotNull(response);
        assertEquals("login successful",response);

    }

    private LoginDto buildLoginDto() {
        return LoginDto.builder()
                .email("email")
                .password("password").build();
    }

    private RegisterUserDto buildRegisterUserDto() {
        return RegisterUserDto.builder().email("email")
                .dateOfBirth("2000-09-07").password("password").build();
    }
}
