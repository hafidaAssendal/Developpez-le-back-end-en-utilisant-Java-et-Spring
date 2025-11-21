package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.*;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import com.chatop.rental.security.AuthenticatedUser;
import com.chatop.rental.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/rentals")
public class RentalController {
  @Autowired
  RentalService rentalService;
  @Autowired
  AuthenticatedUser authenticatedUser;
  @GetMapping
  public ResponseEntity<Map<String, List<RentalResponseDTO>>> getRentals() {
    Map<String, List<RentalResponseDTO>> response = new HashMap<>();
    response.put("rentals", rentalService.getAllRentals());
    return ResponseEntity.ok(response);
  }

  // GET /rentals/{id}
  @GetMapping("/{id}")
  public ResponseEntity<RentalResponseDTO> getRental(@PathVariable Long id) {
    Optional<Rental> rental = rentalService.getRentalById(id);
    if (rental.isEmpty()) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(rentalService.convertEntityToDto(rental));
  }

  @PostMapping
  public ResponseEntity<StatusRentalResponseDTO> postRental(
    @ModelAttribute PostRentalRequestDTO rentalDto,
    @RequestParam("picture") MultipartFile file) throws IOException {
    User ActivedUser= authenticatedUser.get();
    Rental rental = rentalService.createRental(rentalDto, file,ActivedUser);
    return ResponseEntity.ok(new StatusRentalResponseDTO("Rental created ! : "));
  }

  @PutMapping("/{id}")
  public ResponseEntity<StatusRentalResponseDTO> putRental(@PathVariable Long id,
                                                           @ModelAttribute PostRentalRequestDTO rentalDto) {
    User ActivedUser = authenticatedUser.get();
    try {
      Rental updatedRental = rentalService.updateRental(id, rentalDto,ActivedUser);
      return ResponseEntity.ok(new StatusRentalResponseDTO(" rental updated successfully "));

    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
