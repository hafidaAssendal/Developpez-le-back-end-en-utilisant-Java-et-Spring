package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.MessageRequestDTO;
import com.chatop.rental.DTOs.MessageResponseDTO;
import com.chatop.rental.DTOs.StatusRentalResponseDTO;
import com.chatop.rental.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
  @Autowired
  MessageService messageService;
  @PostMapping
  public ResponseEntity<MessageResponseDTO> postMessage(@RequestBody MessageRequestDTO request) {
    try {
      MessageResponseDTO response = messageService.createMessage(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(new MessageResponseDTO("Erreur : " + e.getMessage()));
    }
  }
}
