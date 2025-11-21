package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.entites.User;

import com.chatop.rental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;
  // GET user by ID
  @GetMapping("/{id}")
  public ResponseEntity<GetUserResponseDTO> getUser(@PathVariable Long id) {
    Optional<User> user = userService.getUserById(id);
    if (user.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(userService.convertEntityToDto(user.get()));
  }
}
