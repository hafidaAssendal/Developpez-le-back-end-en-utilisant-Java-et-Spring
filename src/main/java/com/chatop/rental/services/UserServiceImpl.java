package com.chatop.rental.services;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.DTOs.RegisterUserRequestDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements  UserService{
  @Autowired
  UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  ModelMapper modelMapper;

  @Override
  public Optional<User> getUserById(Long id) {
       return userRepository.findById(id);
  }

  @Override
  public Optional<User> getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }


  // 🔹 Enregistrer un nouvel utilisateur
  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }
  @Override
  public GetUserResponseDTO convertEntityToDto( User user) {
    if (user == null) return null;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    return modelMapper.map(user, GetUserResponseDTO.class);

  }

  @Override
  public User convertDTOToEntity(RegisterUserRequestDTO userRequestDTO) {
    return  User.builder()
      .email(userRequestDTO.getEmail())
      .name(userRequestDTO.getName())
      .password(passwordEncoder.encode(userRequestDTO.getPassword()))
      .build();
  }

}
