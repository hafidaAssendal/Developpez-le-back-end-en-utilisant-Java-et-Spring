package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.MessageRequestDTO;
import com.chatop.rental.DTOs.MessageResponseDTO;
import com.chatop.rental.DTOs.StatusRentalResponseDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.security.AuthenticatedUser;
import com.chatop.rental.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;

@RestController
@RequestMapping("/messages")
public class MessageController {
  @Autowired
  MessageService messageService;
  @Autowired
  AuthenticatedUser authenticatedUser;

  @PostMapping
  public ResponseEntity<MessageResponseDTO> postMessage(@RequestBody MessageRequestDTO request) {
    try {
      User user = authenticatedUser.get();
      // remplacer id_user de requestDTO avec id_ de authenticated_user
      request.setUser_id(user.getId());
      System.out.println(request.getUser_id());
      return ResponseEntity.ok( messageService.createMessage(request));
    } catch (Exception e) {
      return ResponseEntity.badRequest()
        .body(new MessageResponseDTO("Error : " + e.getMessage()));
    }
  }


}
