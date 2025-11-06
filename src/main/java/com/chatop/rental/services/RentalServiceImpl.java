package com.chatop.rental.services;

import com.chatop.rental.DTOs.PostRentalRequestDTO;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import com.chatop.rental.repositories.RentalRepository;
import com.chatop.rental.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class RentalServiceImpl implements RentalService {

  @Autowired
  private RentalRepository rentalRepository;
 @Autowired
 private UserRepository userRepository;
  @Autowired
  private ModelMapper modelMapper;

  private final Path uploadDir = Paths.get("uploads");

  public RentalServiceImpl() {
    try {
      Files.createDirectories(uploadDir);
    } catch (IOException e) {
      throw new RuntimeException("Impossible de créer le dossier uploads", e);
    }
  }

  @Override
  public Rental createRental(PostRentalRequestDTO dto, MultipartFile file) throws IOException {

    String pictureName = null;

    if (file != null && !file.isEmpty()) {
      pictureName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // éviter doublons
      Path targetLocation = uploadDir.resolve(pictureName);
      Files.copy(file.getInputStream(), targetLocation);
    }

    // Conversion DTO → Entity
    Rental rental = modelMapper.map(dto, Rental.class);
    // owner Id
    User owner = userRepository.findById(1l)
      .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    rental.setOwner(owner);

    // Assigner le nom du fichier s’il existe
    if (pictureName != null) {
      rental.setPicture(pictureName);
    }
   // Sauvegarde
    return rentalRepository.save(rental);
  }
}
