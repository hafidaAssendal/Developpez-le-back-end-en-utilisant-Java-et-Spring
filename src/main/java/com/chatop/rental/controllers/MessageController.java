package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.MessageRequestDTO;
import com.chatop.rental.DTOs.MessageResponseDTO;
import com.chatop.rental.DTOs.StatusRentalResponseDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.security.AuthenticatedUser;
import com.chatop.rental.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@Tag(name = "Messages", description = "Endpoints pour la gestion des messages")
public class MessageController {
  @Autowired
  MessageService messageService;
  @Autowired
  AuthenticatedUser authenticatedUser;

  // ============================
  // POST MESSAGE
  // ============================
  @PostMapping
  @Operation(
    summary = "Envoyer un message",
    description = "Crée un nouveau message et attache automatiquement l’utilisateur authentifié."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Message créé avec succès",
      content = @Content(schema = @Schema(implementation = MessageResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Erreur dans la requête",
      content = @Content(schema = @Schema(implementation = MessageResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Non authentifié",
      content = @Content
    )
  })
  public ResponseEntity<MessageResponseDTO> postMessage(@RequestBody MessageRequestDTO request) {
    try {
      User user = authenticatedUser.get();
      // Remplacer le user_id du DTO par celui de l’utilisateur authentifié
      request.setUser_id(user.getId());
      return ResponseEntity.ok(messageService.createMessage(request));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
        .body(new MessageResponseDTO("Error : " + e.getMessage()));
    }
  }

}


