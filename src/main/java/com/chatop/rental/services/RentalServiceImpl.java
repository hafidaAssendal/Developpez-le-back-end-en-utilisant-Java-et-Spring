package com.chatop.rental.services;

import com.chatop.rental.DTOs.RentalRequestDTO;
import com.chatop.rental.DTOs.RentalResponseDTO;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import com.chatop.rental.repositories.RentalRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {

  @Autowired
  private RentalRepository rentalRepository;
  @Autowired
  private ModelMapper modelMapper;

  private final Path uploadDir = Paths.get("uploads");
  public RentalServiceImpl() {
    try {
      Files.createDirectories(uploadDir);
    } catch (IOException e) {
      throw new RuntimeException("Uploads folder Not Created : ", e);
    }
  }

  @Override
  public Rental createRental(RentalRequestDTO dto, MultipartFile file, User authenticatedUser) throws IOException {
    Rental rental = modelMapper.map(dto, Rental.class);
    rental.setOwnerId(authenticatedUser);
    try {
      if (file != null && !file.isEmpty()) {
        String originalName = Paths.get(file.getOriginalFilename()).getFileName().toString();
        String pictureName = System.currentTimeMillis() + "_" + originalName;
        Path targetLocation = uploadDir.resolve(pictureName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        rental.setPicture("/api/uploads/" + pictureName);
      }
    } catch (IOException e) {
      throw new RuntimeException(" Error while uploading the file: " + e.getMessage());
    }
    return rentalRepository.save(rental);
  }

  @Override
  public Rental updateRental(Long id, RentalRequestDTO dto, User authenticatedUser) {
    Rental existingRental = rentalRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Rental with this ID : " + id + " not found."));

    if (!existingRental.getOwnerId().getId().equals(authenticatedUser.getId())) {
      throw new AccessDeniedException("You are not the owner of this rental.");
    }

    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    modelMapper.map(dto, existingRental);
    return rentalRepository.save(existingRental);
  }

  @Override
  public Optional<Rental> getRentalById(Long id) {
    return rentalRepository.findById(id);
  }

  @Override
  public List<RentalResponseDTO> getAllRentals() {
    return rentalRepository.findAll().stream()
                           .map(this::convertEntityToDto)
                           .collect(Collectors.toList());
  }

  @Override
  public RentalResponseDTO convertEntityToDto(Rental rental) {
    if (rental == null) return null;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    modelMapper.typeMap(Rental.class, RentalResponseDTO.class)
               .addMappings(mapper -> mapper.skip(RentalResponseDTO::setOwner_id));
    RentalResponseDTO dto = modelMapper.map(rental, RentalResponseDTO.class);

    if (rental.getOwnerId() != null) {
      dto.setOwner_id(rental.getOwnerId().getId());
    }
    return dto;
  }
}
