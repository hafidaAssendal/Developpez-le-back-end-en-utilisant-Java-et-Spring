package com.chatop.rental.services;

import com.chatop.rental.DTOs.MessageRequestDTO;
import com.chatop.rental.DTOs.MessageResponseDTO;
import com.chatop.rental.entites.Message;

import java.io.IOException;

public interface MessageService {
  MessageResponseDTO createMessage(MessageRequestDTO messageDTO) throws IOException;
  Message convertDtoToEntity(MessageRequestDTO dto);
 }
