package com.chatop.rental.controllers;

import com.chatop.rental.entites.User;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

  // GET user by ID
  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    if (id == 1L) {
      User user = new User();
      user.setId(id);
      user.setName("Owner Name");
      user.setEmail("test@test.com");
      user.setPassword("password");
      user.setCreatedAt(LocalDateTime.of(2022, 2, 2, 0, 0));
      user.setUpdatedAt(LocalDateTime.of(2022, 8, 2, 0, 0));
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
