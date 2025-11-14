package com.chatop.rental.services;
import com.chatop.rental.DTOs.MessageRequestDTO;
import com.chatop.rental.DTOs.MessageResponseDTO;
import com.chatop.rental.entites.Message;
import com.chatop.rental.entites.Rental;
import com.chatop.rental.entites.User;
import com.chatop.rental.repositories.MessageRepository;
import com.chatop.rental.repositories.RentalRepository;
import com.chatop.rental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageServiceImpl implements MessageService{

  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private RentalRepository rentalRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public MessageResponseDTO createMessage(MessageRequestDTO messageDTO) throws IOException {
    Message message = convertDtoToEntity(messageDTO);
    messageRepository.save(message);
    return new MessageResponseDTO("Message sent successfully");
  }
  @Override
  public Message convertDtoToEntity(MessageRequestDTO dto) {
    Message message = new Message();
    message.setMessage(dto.getMessage());
    // récupérer les entités liées
    Rental rental = rentalRepository.findById(dto.getRental_id()).orElseThrow(() -> new RuntimeException("Rental not found"));
    User user = userRepository.findById(dto.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
    message.setRental(rental);
    message.setUser(user);
    return message;
  }

  @Override
  public MessageResponseDTO convertEntityToDto(Message message) {
    return new MessageResponseDTO(message.getMessage());
  }
}
