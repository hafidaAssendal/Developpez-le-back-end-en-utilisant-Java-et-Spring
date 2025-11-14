package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.DTOs.LoginUserRequestDTO;
import com.chatop.rental.DTOs.RegisterUserRequestDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.security.JwtService;
import com.chatop.rental.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  UserService userService;
  @Autowired
  PasswordEncoder passwordEncoder;
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  JwtService jwtService;

  //inscription auth/register
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
    User user = User.builder()
      .email(registerUserRequestDTO.getEmail())
      .name(registerUserRequestDTO.getName())
      .password(passwordEncoder.encode(registerUserRequestDTO.getPassword()))
      .build();
    userService.saveUser(user);
    return ResponseEntity.ok("Utilisateur enregistré avec succès : " + registerUserRequestDTO.getEmail());
  }

  //Login
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginUserRequestDTO loginDTO) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDTO.getLogin(), loginDTO.getPassword())
    );
    String token = jwtService.generateToken(loginDTO.getLogin());
    return ResponseEntity.ok("{\"token\":\"" + token + "\"}");
  }

  @GetMapping("/me")
  public ResponseEntity<GetUserResponseDTO> getAuthUser(HttpServletRequest request) {
     String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return ResponseEntity.status(401).build(); // Pas de token fourni
    }

    String token = authHeader.substring(7);
    String email;

    try {
      email = jwtService.extractEmail(token);
    } catch (Exception e) {
      return ResponseEntity.status(401).build();
    }

    Optional<User> activeUser = userService.getUserByEmail(email);
    if (activeUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    GetUserResponseDTO responseDTO = userService.convertEntityToDto(activeUser);
    return ResponseEntity.ok(responseDTO);
  }

  //profile
//  @GetMapping("/me")
//  public ResponseEntity<GetUserResponseDTO> getAuthUser() {
//    Optional<User> activeUser = userService.getUserById(1L);
//    System.out.println("the active user : " + activeUser.toString());
//    //System.out.println("the active user : "+activeUser.);
//    return ResponseEntity.ok(userService.convertEntityToDto(activeUser));
//  }

}
