package com.chatop.rental.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRentalRequestDTO {
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String description;

}
