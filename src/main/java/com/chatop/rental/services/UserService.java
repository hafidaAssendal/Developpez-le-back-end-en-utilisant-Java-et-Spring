package com.chatop.rental.services;
import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.entites.User;
import java.util.Optional;

public interface UserService {
  public User saveUser(User user);
  Optional<User> getUserById(Long id);
  Optional<User> getUserByEmail(String email);
  GetUserResponseDTO convertEntityToDto (Optional<User>  user);

}
