package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.*;
import com.chatop.rental.entites.User;
import com.chatop.rental.security.AuthenticatedUser;
import com.chatop.rental.security.JwtService;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for managing user authentication")

public class AuthController {
  @Autowired
  UserService userService;
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  JwtService jwtService;
  @Autowired
  AuthenticatedUser authenticatedUser;

  @PostMapping("/register")
  @Operation(summary = "Register a new user", description = "Creates a new user and returns a JWT token.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Registration successful",
      content = @Content(schema = @Schema(implementation = UserTokenResponseDTO.class))
    ),
  })
  public ResponseEntity<UserTokenResponseDTO> register(@RequestBody UserRegisterRequestDTO registerUserRequestDTO) {
      User user = userService.convertDTOToEntity(registerUserRequestDTO);
      User savedUser = userService.saveUser(user);
      String token = jwtService.generateToken(savedUser.getEmail());
      return ResponseEntity.ok(new UserTokenResponseDTO(token));
  }

  @PostMapping("/login")
  @Operation(summary = "Login", description = "Authenticates the user and returns a JWT.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Login successful",
      content = @Content(schema = @Schema(implementation = UserTokenResponseDTO.class))
    ),
    @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content)
  })
  public ResponseEntity<UserTokenResponseDTO> login(@RequestBody UserLoginRequestDTO loginDTO) {

      Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),
                                                loginDTO.getPassword()));
      String token = jwtService.generateToken(authentication.getName());
      return ResponseEntity.ok(new UserTokenResponseDTO(token));
   }

  @GetMapping("/me")
  @Operation(
    summary = "Current authenticated user profile",
    description = "Returns the information of the currently authenticated user."
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "User found",
      content = @Content(schema = @Schema(implementation = UserGetResponseDTO.class))
    ),
    @ApiResponse(responseCode = "401", description = "User not authenticated", content = @Content)
  })
  public ResponseEntity<UserGetResponseDTO> getAuthUser() {
    User activeUser = authenticatedUser.get();
    UserGetResponseDTO userDto = userService.convertEntityToDto(activeUser);
    return ResponseEntity.ok(userDto);
  }
}


