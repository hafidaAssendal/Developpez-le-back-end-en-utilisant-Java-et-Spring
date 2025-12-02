package com.chatop.rental.services;
import com.chatop.rental.DTOs.RentalRequestDTO;
import com.chatop.rental.DTOs.RentalResponseDTO;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RentalService {
  Rental createRental(RentalRequestDTO dto, MultipartFile file, User authenticatedUser) throws IOException;
  Rental updateRental(Long id, RentalRequestDTO dto, User authenticatedUser);
  List<RentalResponseDTO> getAllRentals();
  Optional<Rental> getRentalById(Long id);
  RentalResponseDTO convertEntityToDto(Rental rental);
}
