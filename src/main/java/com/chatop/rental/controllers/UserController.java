package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.entites.User;

import com.chatop.rental.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Endpoints liés aux utilisateurs")

public class UserController {
  @Autowired
  UserService userService;
  // ============================
  // GET USER BY ID
  // ============================
  @GetMapping("/{id}")
  @Operation(
    summary = "Récupérer un utilisateur par son ID",
    description = "Retourne les informations d’un utilisateur existant en fonction de son ID."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Utilisateur trouvé",
      content = @Content(schema = @Schema(implementation = GetUserResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Utilisateur non trouvé",
      content = @Content
    )
  })

  public ResponseEntity<GetUserResponseDTO> getUser(@PathVariable Long id) {
    Optional<User> user = userService.getUserById(id);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(userService.convertEntityToDto(user.get()));
  }
}


