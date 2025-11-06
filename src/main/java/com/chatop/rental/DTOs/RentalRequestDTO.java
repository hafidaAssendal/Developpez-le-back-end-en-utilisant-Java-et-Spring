package com.chatop.rental.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDTO {
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String picture;
  private String description;
  private Long ownerId;

}
