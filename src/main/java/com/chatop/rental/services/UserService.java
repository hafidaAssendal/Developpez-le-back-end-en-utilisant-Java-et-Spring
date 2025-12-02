package com.chatop.rental.services;
import com.chatop.rental.DTOs.UserGetResponseDTO;
import com.chatop.rental.DTOs.UserRegisterRequestDTO;
import com.chatop.rental.entites.User;
import java.util.Optional;

public interface UserService {
  User saveUser(User user);
  Optional<User> getUserById(Long id);
  Optional<User> getUserByEmail(String email);
  UserGetResponseDTO convertEntityToDto (User  user);
  User convertDTOToEntity (UserRegisterRequestDTO userRequestDTO);

}
