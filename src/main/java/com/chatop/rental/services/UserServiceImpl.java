package com.chatop.rental.services;
import com.chatop.rental.DTOs.UserGetResponseDTO;
import com.chatop.rental.DTOs.UserRegisterRequestDTO;
import com.chatop.rental.entites.User;
import com.chatop.rental.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

  @Override
  public User saveUser(User user) {
         return userRepository.save(user);
  }

  @Override
  public UserGetResponseDTO convertEntityToDto(User user) {
    if (user == null) return null;
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    return modelMapper.map(user, UserGetResponseDTO.class);

  }

  @Override
  public User convertDTOToEntity(UserRegisterRequestDTO userRequestDTO) {
    return  User.builder()
                .email(userRequestDTO.getEmail())
                .name(userRequestDTO.getName())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .build();
  }

}
