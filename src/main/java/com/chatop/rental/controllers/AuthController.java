package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.DTOs.JwtUserResponseDTO;
import com.chatop.rental.DTOs.LoginUserRequestDTO;
import com.chatop.rental.DTOs.RegisterUserRequestDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.security.AuthenticatedUser;
import com.chatop.rental.security.JwtService;
import com.chatop.rental.security.UserDetailsServiceImpl;
import com.chatop.rental.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  UserService userService;

  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  JwtService jwtService;
  @Autowired
  UserDetailsServiceImpl userDetailsService;
  @Autowired
  AuthenticatedUser authenticatedUser;


  //inscription auth/register
  @PostMapping("/register")
  public ResponseEntity<JwtUserResponseDTO> register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
    User savedUser = userService.saveUser(userService.convertDTOToEntity(registerUserRequestDTO));
    String token = jwtService.generateToken(savedUser.getEmail());
    return ResponseEntity.ok(new JwtUserResponseDTO(token));
  }

  @PostMapping("/login")
  public ResponseEntity<JwtUserResponseDTO> login(@RequestBody LoginUserRequestDTO loginDTO) {
    // Authentifier l'utilisateur
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDTO.getLogin()
        , loginDTO.getPassword()));
    //  Générer le token
    String token = jwtService.generateToken(loginDTO.getLogin());
    return ResponseEntity.ok(new JwtUserResponseDTO(token));
  }

  @GetMapping("/me")
  public ResponseEntity<GetUserResponseDTO> getAuthUser() {
    User activedUser = authenticatedUser.get();
    return ResponseEntity.ok(userService.convertEntityToDto(activedUser));
  }
}
