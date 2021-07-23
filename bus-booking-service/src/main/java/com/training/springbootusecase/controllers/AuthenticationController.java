package com.training.springbootusecase.controllers;

import com.training.springbootusecase.config.JwtTokenUtil;
import com.training.springbootusecase.dto.LoginDto;
import com.training.springbootusecase.entity.AuthToken;
import com.training.springbootusecase.entity.User;
import com.training.springbootusecase.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    private final UserService userService;

    @PostMapping(value = "/generate-token")
    public ResponseEntity<AuthToken> registerUser(@RequestBody LoginDto loginDto) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        final User user = userService.getUserByEmail(loginDto.getEmail());
        final String token = jwtTokenUtil.generateToken(user);
        return new ResponseEntity<>(AuthToken.builder()
                .email(user.getEmail())
                .token(token).build(), OK);
    }

}