package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.DTOs.LoginUserRequestDTO;
import com.chatop.rental.DTOs.RegisterUserRequestDTO;
import com.chatop.rental.DTOs.RentalRequestDTO;
import com.chatop.rental.entites.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  //inscription auth/register
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
    return ResponseEntity.ok("Utilisateur enregistré avec succès : "+registerUserRequestDTO.toString());

  }
  //Login
  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginUserRequestDTO login) {

    if ("test@test.com".equals(login.getLogin()) && "test!31".equals(login.getPassword())) {
      return ResponseEntity.ok("{token : jwt}  "+ login.toString());
    } else {
      return ResponseEntity.ok("Authentification invalide ");
    }
  }
  //profile
  @GetMapping("/me")
  public ResponseEntity<GetUserResponseDTO> getAuthUser() {
    GetUserResponseDTO user = new GetUserResponseDTO();
    user.setId(1L);
    user.setName("Test Test");
    user.setEmail("test@test.com");
    user.setCreatedAt(LocalDateTime.of(2022, 2, 2, 0, 0));
    user.setUpdatedAt(LocalDateTime.of(2022, 8, 2, 0, 0));
    return ResponseEntity.ok(user);
  }

}
