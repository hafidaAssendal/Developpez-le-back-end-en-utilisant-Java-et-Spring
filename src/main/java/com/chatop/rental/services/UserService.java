package com.chatop.rental.services;
import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.entites.User;
import java.util.Optional;

public interface UserService {
  Optional<User> getUserById(Long id);
  Optional<User> getUserByEmail(String email);
  // ###########   Ajouter la méthode convertEntityToDto()
  GetUserResponseDTO convertEntityToDto (Optional<User>  user);
}
