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


@Service
public class MessageServiceImpl implements MessageService {
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private RentalRepository rentalRepository;
  @Autowired
  private UserRepository userRepository;

  @Override
  public MessageResponseDTO createMessage(MessageRequestDTO messageDTO){
    messageRepository.save(convertDtoToEntity(messageDTO));
    return new MessageResponseDTO("Message sent successfully");
  }

  @Override
  public Message convertDtoToEntity(MessageRequestDTO dto) {
    Rental rental = rentalRepository.findById(dto.getRental_id())
                                     .orElseThrow(() -> new RuntimeException("Rental not found"));
    User user = userRepository.findById(dto.getUser_id())
                                     .orElseThrow(() -> new RuntimeException("User not found"));
    return Message.builder()
                  .message(dto.getMessage())
                  .user(user)
                  .rental(rental)
                  .build();
  }
}
