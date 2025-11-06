package com.chatop.rental.services;

import com.chatop.rental.DTOs.PostRentalRequestDTO;
import com.chatop.rental.entites.Rental;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface RentalService {
  Rental createRental(PostRentalRequestDTO dto, MultipartFile file) throws IOException;

}
