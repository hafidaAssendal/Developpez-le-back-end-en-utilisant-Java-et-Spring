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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints pour gérer l’authentification utilisateur")

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


  // ============================
  // REGISTER
  // ============================
  @PostMapping("/register")
  @Operation(summary = "Inscription", description = "Crée un nouvel utilisateur et retourne un JWT.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Inscription réussie",
      content = @Content(schema = @Schema(implementation = JwtUserResponseDTO.class))),
    @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
  })
  public ResponseEntity<JwtUserResponseDTO> register(@RequestBody RegisterUserRequestDTO registerUserRequestDTO) {
    User savedUser = userService.saveUser(userService.convertDTOToEntity(registerUserRequestDTO));
    String token = jwtService.generateToken(savedUser.getEmail());
    return ResponseEntity.ok(new JwtUserResponseDTO(token));
  }


  // ============================
  // LOGIN
  // ============================
  @PostMapping("/login")
  @Operation(summary = "Connexion", description = "Authentifie l’utilisateur et retourne un JWT.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Connexion réussie",
      content = @Content(schema = @Schema(implementation = JwtUserResponseDTO.class))),
    @ApiResponse(responseCode = "401", description = "Authentification échouée", content = @Content)
  })
  public ResponseEntity<JwtUserResponseDTO> login(@RequestBody LoginUserRequestDTO loginDTO) {
    // Authentifier l'utilisateur
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(loginDTO.getEmail()
        , loginDTO.getPassword()));
    //  Générer le token
    String token = jwtService.generateToken(loginDTO.getEmail());
    return ResponseEntity.ok(new JwtUserResponseDTO(token));
  }

  // ============================
  // GET AUTH USER
  // ============================
  @GetMapping("/me")
  @Operation(summary = "Profil utilisateur connecté",
    description = "Retourne les informations de l’utilisateur actuellement authentifié.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
      content = @Content(schema = @Schema(implementation = GetUserResponseDTO.class))),
    @ApiResponse(responseCode = "401", description = "Accès non autorisé", content = @Content)
  })
  public ResponseEntity<GetUserResponseDTO> getAuthUser() {
    User activedUser = authenticatedUser.get();
    return ResponseEntity.ok(userService.convertEntityToDto(activedUser));
  }
}


