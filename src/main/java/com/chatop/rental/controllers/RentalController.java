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
@Tag(name = "Rentals", description = "Endpoints pour la gestion des locations (rentals)")
public class RentalController {
  @Autowired
  RentalService rentalService;
  @Autowired
  AuthenticatedUser authenticatedUser;

  // ============================
  // GET /rentals
  // ============================
  @GetMapping
  @Operation(
    summary = "Liste des locations",
    description = "Retourne la liste complète des rentals disponibles."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Liste récupérée avec succès",
      content = @Content(schema = @Schema(implementation = RentalResponseDTO.class))
    )
  })
  public ResponseEntity<Map<String, List<RentalResponseDTO>>> getRentals() {
    Map<String, List<RentalResponseDTO>> response = new HashMap<>();
    response.put("rentals", rentalService.getAllRentals());
    return ResponseEntity.ok(response);
  }

  // ============================
  // GET /rentals/{id}
  // ============================
  @GetMapping("/{id}")
  @Operation(
    summary = "Récupérer une location par ID",
    description = "Retourne une location précise si elle existe."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Rental trouvé",
      content = @Content(schema = @Schema(implementation = RentalResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Rental introuvable",
      content = @Content
    ),@ApiResponse(
    responseCode = "401",
    description = "Non authentifié",
    content = @Content
  )

  })

  public ResponseEntity<RentalResponseDTO> getRental(@PathVariable Long id) {
    Optional<Rental> rental = rentalService.getRentalById(id);
    if (rental.isEmpty()) return ResponseEntity.notFound().build();
    return ResponseEntity.ok(rentalService.convertEntityToDto(rental));
  }

  // ============================
  // POST /rentals
  // ============================
  @PostMapping
  @Operation(
    summary = "Créer une nouvelle location",
    description = "Crée un rental avec une image (upload Multipart) et les données de formulaire."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "Rental créé avec succès",
      content = @Content(schema = @Schema(implementation = StatusRentalResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "400",
      description = "Requête invalide",
      content = @Content(schema = @Schema(implementation = StatusRentalResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Non authentifié",
      content = @Content
    )
  })
  public ResponseEntity<StatusRentalResponseDTO> postRental(
    @ModelAttribute PostRentalRequestDTO rentalDto,
    @RequestParam("picture") MultipartFile file) throws IOException {
    User ActivedUser = authenticatedUser.get();
    Rental rental = rentalService.createRental(rentalDto, file, ActivedUser);
    return ResponseEntity.ok(new StatusRentalResponseDTO("Rental created ! : "));
  }

  // ============================
  // PUT /rentals/{id}
  // ============================
  @PutMapping("/{id}")
  @Operation(
    summary = "Mettre à jour une location",
    description = "Met à jour un rental existant. Ne modifie pas l'image."
  )
  @ApiResponses({
    @ApiResponse(
      responseCode = "200",
      description = "rental updated successfully",
      content = @Content(schema = @Schema(implementation = StatusRentalResponseDTO.class))
    ),
    @ApiResponse(
      responseCode = "404",
      description = "Rental trouvable",
      content = @Content
    ),
    @ApiResponse(
      responseCode = "401",
      description = "Utilisateur non authetifié",
      content = @Content
    )
  })
  public ResponseEntity<StatusRentalResponseDTO> putRental(@PathVariable Long id,
                                                           @ModelAttribute PostRentalRequestDTO rentalDto) {
    User ActivedUser = authenticatedUser.get();
    try {
      Rental updatedRental = rentalService.updateRental(id, rentalDto, ActivedUser);
      return ResponseEntity.ok(new StatusRentalResponseDTO(" rental updated successfully "));
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
