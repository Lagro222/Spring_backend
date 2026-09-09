package com.store.app.controllers.auth;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.app.DTOs.UserRequestDTO;
import com.store.app.entities.User;
import com.store.app.enums.Role;
import com.store.app.security.JwtService;
import com.store.app.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  public final UserService user_service;
  public final JwtService jwt_servie;
  public final PasswordEncoder passwrod_encoder;
  public final AuthenticationManager auth_manger;

  @RequestMapping("/register")
  public Map<String,String> register(@Valid @RequestBody UserRequestDTO user_dto){
    User user = new User();
    user.setName(user_dto.getName());
    user.setFirstname(user_dto.getFirstname());
    user.setEmail(user_dto.getEmail());
    user.setPassword(passwrod_encoder.encode(user_dto.getPassword()));
    user.setRole(Role.USER);

    user_service.create_user(user);

    String token = jwt_servie.generateToken(user);

    return Map.of("token", token);
   
  }

  @PostMapping("/login")
  public Map<String, String> login(@Valid @RequestBody UserRequestDTO user_dto){
    auth_manger.authenticate(new UsernamePasswordAuthenticationToken(user_dto.getEmail(), user_dto.getPassword()));
    User user = (User) user_service.loadUserByUsername(user_dto.getEmail());
    String token = jwt_servie.generateToken(user);
    return Map.of("token",token);
  }


}
