package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.MessageRequestDTO;
import com.chatop.rental.DTOs.StatusRentalResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
  @PostMapping
  public ResponseEntity<StatusRentalResponseDTO> postMessage(@RequestBody MessageRequestDTO request) {
    StatusRentalResponseDTO response = new StatusRentalResponseDTO("Message send with success : "+request.getMessage());
    return ResponseEntity.ok(response);
  }
}
