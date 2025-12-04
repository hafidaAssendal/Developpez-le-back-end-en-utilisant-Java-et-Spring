package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.MessageRequestDTO;
import com.chatop.rental.DTOs.MessageResponseDTO;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@Tag(name = "Messages", description = "Endpoints  for managing messages")
public class MessageController {
  @Autowired
  MessageService messageService;
  @Autowired
  AuthenticatedUser authenticatedUser;

  @PostMapping
  @Operation(
    summary = "Send a message",
    description = "Creates a new message and automatically associates it with the currently authenticated user."
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200",description = "Message successfully created",
      content = @Content(schema = @Schema(implementation = MessageResponseDTO.class))
    ),
    @ApiResponse(responseCode = "400",description = "Bad request",
      content = @Content(schema = @Schema(implementation = MessageResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "401", description = "User not authenticated or access denied",
      content = @Content(schema = @Schema(implementation = MessageResponseDTO.class))
    )
  })
  public MessageResponseDTO postMessage(@RequestBody MessageRequestDTO request) {
    User user = authenticatedUser.get(); // 401
    Long authenticatedUserId = user.getId();
    if (!authenticatedUserId.equals(request.getUser_id())) {
       throw new IllegalArgumentException("Not allowed user."); //400
    }
    return messageService.createMessage(request);

  }
}


