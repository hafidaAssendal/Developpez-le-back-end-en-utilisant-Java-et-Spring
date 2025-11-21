package com.chatop.rental.services;
import com.chatop.rental.DTOs.PostRentalRequestDTO;
import com.chatop.rental.DTOs.RentalResponseDTO;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import com.chatop.rental.repositories.RentalRepository;
import com.chatop.rental.repositories.UserRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
  public Rental createRental(PostRentalRequestDTO dto, MultipartFile file, User authenticatedUser) throws IOException {
   // gestion de fichier
    String pictureName = null;
    if (file != null && !file.isEmpty()) {
      pictureName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // éviter doublons
      Path targetLocation = uploadDir.resolve(pictureName);
      Files.copy(file.getInputStream(), targetLocation);
    }
    // Conversion DTO → Entity
    Rental rental = modelMapper.map(dto, Rental.class);
    // owner Id
    rental.setOwner(authenticatedUser);
    // Assigner le nom du fichier s’il existe
    if (pictureName != null) {
      rental.setPicture(pictureName);
    }
   // Sauvegarde
    return rentalRepository.save(rental);
  }

  @Override
  public Rental updateRental(Long id, PostRentalRequestDTO dto, User authenticatedUser) {

    Rental existingRental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found with id: " + id));
    // Vérifier l’utilisateur est le propriétaire
    if (!existingRental.getOwner().getId().equals(authenticatedUser.getId())) {
      throw new AccessDeniedException("You are not the owner of this rental");
    }

    if (dto.getName() != null) existingRental.setName(dto.getName());
    if (dto.getPrice() != null) existingRental.setPrice(dto.getPrice());
    if (dto.getSurface() != null) existingRental.setSurface(dto.getSurface());
    if (dto.getDescription() != null) existingRental.setDescription(dto.getDescription());
    return rentalRepository.save(existingRental);



  }

  @Override
  public Optional<Rental> getRentalById(Long id) {
    return rentalRepository.findById(id) ;
  }

  @Override
  public List<RentalResponseDTO> getAllRentals() {

    return rentalRepository.findAll().stream()// ajouter la liste dans un stream
      .map(rental -> convertEntityToDto(Optional.ofNullable(rental)))// appliquer la fonction convert pour chaque element avec conversion de rental à optional
      .collect(Collectors.toList()); // converti en liste;
  }

  @Override
  public RentalResponseDTO convertEntityToDto(Optional<Rental> rental) {
    if (rental.isPresent()) {
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
      return modelMapper.map(rental.get(), RentalResponseDTO.class);
    }
    return null; //  rental null

  }

}
