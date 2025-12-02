package com.chatop.rental.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGetResponseDTO {
  private Long id;
  private String name;
  private String email;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
}
