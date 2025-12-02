package com.chatop.rental.controllers;

import com.chatop.rental.DTOs.*;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import com.chatop.rental.security.AuthenticatedUser;
import com.chatop.rental.services.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rentals", description = "Endpoints for managing rentals")
public class RentalController {
  @Autowired
  RentalService rentalService;
  @Autowired
  AuthenticatedUser authenticatedUser;

  @GetMapping
  @Operation(summary = "List all rentals",description = "Returns the complete list of available rentals.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Rental list retrieved successfully",
      content = @Content(schema = @Schema(implementation = RentalResponseDTO.class))),
    @ApiResponse(responseCode = "401",description = "Unauthorized: authentication failed",content = @Content)
  })
  public ResponseEntity<Map<String, List<RentalResponseDTO>>> getRentals() {
    Map<String, List<RentalResponseDTO>> response = new HashMap<>();
    response.put("rentals", rentalService.getAllRentals());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  @Operation( summary = "Get rental by ID",description = "Returns  rental by ID.")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Rental found",
      content = @Content(schema = @Schema(implementation = RentalResponseDTO.class))),
    @ApiResponse(responseCode = "404",description = "Rental not found",content = @Content),
    @ApiResponse(responseCode = "401",description = "Unauthorized: authentication required",content = @Content)
  })
  public ResponseEntity<RentalResponseDTO> getRental(@PathVariable Long id) {
    Optional<Rental> rental = rentalService.getRentalById(id);
    if (rental.isEmpty()) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(rentalService.convertEntityToDto(rental.get()));
  }

  @PostMapping
  @Operation(summary = "Create a new rental",description = "Creates a new rental with an image (multipart upload) and form data.")
  @ApiResponses({
    @ApiResponse(responseCode = "200",description = "Rental created successfully",
      content = @Content(schema = @Schema(implementation = StatusRentalResponseDTO.class))),
    @ApiResponse(responseCode = "401",description = "Unauthorized: user not authenticated",content = @Content)
  })
  public ResponseEntity<StatusRentalResponseDTO> postRental(
    @ModelAttribute RentalRequestDTO rentalDto,
    @RequestParam("picture") MultipartFile file) throws IOException {
    User ActivedUser = authenticatedUser.get();
    Rental rental = rentalService.createRental(rentalDto, file, ActivedUser);
    return ResponseEntity.ok(new StatusRentalResponseDTO("Rental created ! : "));
  }

  @PutMapping("/{id}")
  @Operation(
    summary = "Update a rental",
    description = "Updates an existing rental"
  )
  @ApiResponses({
    @ApiResponse(responseCode = "200",description = "Rental updated successfully",
      content = @Content(schema = @Schema(implementation = StatusRentalResponseDTO.class))),
    @ApiResponse(responseCode = "404",description = "Rental not found",content = @Content),
    @ApiResponse(responseCode = "401",description = "Unauthorized: user not authenticated",content = @Content)
  })
  public ResponseEntity<StatusRentalResponseDTO> putRental(@PathVariable Long id,
                                                           @ModelAttribute RentalRequestDTO rentalDto) {
    User ActivedUser = authenticatedUser.get();
    try {
      rentalService.updateRental(id, rentalDto, ActivedUser);
      return ResponseEntity.ok(new StatusRentalResponseDTO(" rental updated successfully "));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}


