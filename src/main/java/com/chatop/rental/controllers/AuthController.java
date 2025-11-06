package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.DTOs.LoginUserRequestDTO;
import com.chatop.rental.DTOs.RegisterUserRequestDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;


@RestController
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  UserService userService;

  //inscription auth/register
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
    return ResponseEntity.ok("Utilisateur enregistré avec succès : " + registerUserRequestDTO.toString());

  }

  //Login
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginUserRequestDTO login) {

    if ("test@test.com".equals(login.getLogin()) && "test!31".equals(login.getPassword())) {
      return ResponseEntity.ok("{token : jwt}  " + login.toString());
    } else {
      return ResponseEntity.ok("Authentification invalide ");
    }
  }

  //profile
  @GetMapping("/me")
  public ResponseEntity<GetUserResponseDTO> getAuthUser() {
    Optional<User> activeUser = userService.getUserById(1L);
    System.out.println("the active user : " + activeUser.toString());
  //System.out.println("the active user : "+activeUser.);
    return ResponseEntity.ok(userService.convertEntityToDto(activeUser));
  }

}
