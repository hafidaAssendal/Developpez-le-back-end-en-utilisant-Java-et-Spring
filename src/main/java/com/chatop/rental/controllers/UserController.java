package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.UserGetResponseDTO;
import com.chatop.rental.entites.User;

import com.chatop.rental.security.AuthenticatedUser;
import com.chatop.rental.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/user")

@Tag(name = "User", description = "Endpoint for managing and retrieving user information")

public class UserController {
  @Autowired
  UserService userService;
  @Autowired
  AuthenticatedUser authenticatedUser;

  @GetMapping("/{id}")
  @Operation(
    summary = "Get a user by ID",description = "Returns the information of an existing user by his ID."
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200",description = "User found",
      content = @Content(schema = @Schema(implementation = UserGetResponseDTO.class))
    ),
    @ApiResponse(responseCode = "404",description = "User not found",content = @Content),
    @ApiResponse(responseCode = "401",description = "User not authenticated",content = @Content)
  })
  public ResponseEntity<UserGetResponseDTO> getUser(@PathVariable Long id) {

       User user = userService.getUserById(id)
         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));// 404
       return ResponseEntity.ok(userService.convertEntityToDto(user));
  }
}


