package com.chatop.rental.services;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.DTOs.PostRentalRequestDTO;
import com.chatop.rental.DTOs.RentalResponseDTO;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RentalService {
  Rental createRental(PostRentalRequestDTO dto, MultipartFile file) throws IOException;

  Rental updateRental(Long id, PostRentalRequestDTO dto);

  Optional<Rental> getRentalById(Long id);

  List<RentalResponseDTO> getAllRentals();

  public RentalResponseDTO convertEntityToDto(Optional<Rental> rental);
}
