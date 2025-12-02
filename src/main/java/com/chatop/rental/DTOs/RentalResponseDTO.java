package com.chatop.rental.DTOs;

import com.chatop.rental.entites.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalResponseDTO {
  private Long id;
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  private String description;
  private LocalDateTime created_at;
  private LocalDateTime updated_at;
  private Long owner_id;
}
