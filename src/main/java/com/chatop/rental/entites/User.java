package com.chatop.rental.entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "USERS", indexes = {
  @Index(name = "USERS_index", columnList = "email", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String email;
  private String name;
  private String password;
  @Column(name = "created_at")
  private LocalDateTime createdAt;
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

}
