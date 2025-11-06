package com.chatop.rental.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDTO {
  private String message;
  private Long userId;
  private Long rentalId;

}
