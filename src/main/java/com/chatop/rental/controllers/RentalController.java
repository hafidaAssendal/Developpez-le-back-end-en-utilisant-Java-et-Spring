package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.*;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class RentalController {
  @Autowired
  RentalService rentalService;

  @GetMapping
  public ResponseEntity<List<RentalResponseDTO>> getRentals() {
    return ResponseEntity.ok(rentalService.getAllRentals());
  }

  // GET /rentals/{id}
  @GetMapping("/{id}")
  public ResponseEntity<RentalResponseDTO> getRental(@PathVariable Long id) {
    Optional<Rental> rental = rentalService.getRentalById(id);
    if (rental.isEmpty()) {
      // S'il n'existe pas, on renvoie une erreur 404
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(rentalService.convertEntityToDto(rental));


  }

  @PostMapping
  public ResponseEntity<StatusRentalResponseDTO> postRental(
    @ModelAttribute PostRentalRequestDTO rentalDto,
    @RequestParam("picture") MultipartFile file) throws IOException {
    Rental rental = rentalService.createRental(rentalDto, file);
    return ResponseEntity.ok(new StatusRentalResponseDTO("Rental created ! : "));
  }

  @PutMapping("/{id}")
  public ResponseEntity<StatusRentalResponseDTO> putRental(@PathVariable Long id,
                                                           @ModelAttribute PostRentalRequestDTO rentalDto) {
    try {
      Rental updatedRental = rentalService.updateRental(id, rentalDto);
      return ResponseEntity.ok(new StatusRentalResponseDTO(" rental updated successfully "));

    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }


  }


/*
  @PostMapping
  public ResponseEntity<StatusRentalResponseDTO> postRental(
    @RequestParam String name,
    @RequestParam BigDecimal price,
    @RequestParam BigDecimal surface,
    @RequestParam String description,
    @RequestParam("picture") MultipartFile file) {
    PostRentalRequestDTO rental = new PostRentalRequestDTO();
    rental.setName(name);
    rental.setPrice(price);
    rental.setSurface(surface);
    rental.setDescription(description);
    rental.setPicture(file.getOriginalFilename());
    System.out.println("Picture : " + file.getOriginalFilename());
    System.out.println(rental.toString());
    return ResponseEntity.ok(new StatusRentalResponseDTO("Rental created ! : "+rental.toString()));
  }

 */

}
