package com.chatop.rental.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutRentalRequestDTO {
  private String name;
  private BigDecimal surface;
  private BigDecimal price;
  private String description;


}
