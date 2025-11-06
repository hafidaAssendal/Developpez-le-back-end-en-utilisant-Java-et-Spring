package com.chatop.rental.services;

import com.chatop.rental.DTOs.GetUserResponseDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements  UserService{
  @Autowired
  UserRepository userRepository;

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

  @Override
  public GetUserResponseDTO convertEntityToDto(Optional<User>  user) {
    if (user.isPresent()) {
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
      return modelMapper.map(user.get(), GetUserResponseDTO.class);
    }
    return null; //  Optionaluser null

  }

}
