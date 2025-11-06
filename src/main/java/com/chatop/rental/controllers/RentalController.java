package com.chatop.rental.controllers;
import com.chatop.rental.DTOs.MessageRentalResponseDTO;
import com.chatop.rental.DTOs.PostRentalRequestDTO;
import com.chatop.rental.DTOs.PutRentalRequestDTO;
import com.chatop.rental.DTOs.RentalResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rentals")
public class RentalController {

  @GetMapping
  public ResponseEntity<List<RentalResponseDTO>> getAllRentals() {
    List<RentalResponseDTO> rentals = new ArrayList<>();
    // Rental 1
    RentalResponseDTO rental1 = new RentalResponseDTO();
    // Rental 2
    RentalResponseDTO rental2 = new RentalResponseDTO();

    rental1.setId(1L);
    rental1.setName("test house 1");
    rental1.setSurface(new BigDecimal("432"));
    rental1.setPrice(new BigDecimal("300"));
    rental1.setPicture("https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg");
    rental1.setDescription("Lorem ipsum dolor sit et eros, et viverra libero tincidunt a. Nunc vel nisi vulputate, sodales massa eu, varius erat.");
    rental1.setOwnerId(1L);
    rental1.setCreatedAt("2012/12/02");
    rental1.setUpdatedAt("2014/12/02");

    rental2.setId(2L);
    rental2.setName("test house 2");
    rental2.setSurface(new BigDecimal("154"));
    rental2.setPrice(new BigDecimal("200"));
    rental2.setPicture("https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg");
    rental2.setDescription(rental1.getDescription()); // même description pour simplifier
    rental2.setOwnerId(2L);
    rental2.setCreatedAt("2012/12/02");
    rental2.setUpdatedAt("2014/12/02");
    rentals.add(rental1);
    rentals.add(rental2);
    return ResponseEntity.ok(rentals);
  }
  // GET /rentals/{id}
  @GetMapping("/{id}")
  public ResponseEntity<RentalResponseDTO> getRentalById(@PathVariable Long id) {
    if (id == 1L) {
      RentalResponseDTO rental = new RentalResponseDTO();
      rental.setId(1L);
      rental.setName("test house 1");
      rental.setSurface(new BigDecimal("432"));
      rental.setPrice(new BigDecimal("300"));
      rental.setPicture("https://blog.technavio.org/wp-content/uploads/2018/12/Online-House-Rental-Sites.jpg");
      rental.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam a lectus eleifend, varius massa ac, mollis tortor.");
      rental.setOwnerId(1L);
      rental.setCreatedAt("2012/12/02");
      rental.setUpdatedAt("2014/12/02");
      return ResponseEntity.ok(rental);
    }

    // S'il n'existe pas, on renvoie une erreur 404
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity<MessageRentalResponseDTO> createRental(
    @RequestParam String name,
    @RequestParam BigDecimal price,
    @RequestParam BigDecimal surface,
    @RequestParam String description,
    @RequestParam("picture") MultipartFile file) {
    PostRentalRequestDTO rental = new PostRentalRequestDTO();
    rental.setName(name);
    rental.setPrice(price);
    rental.setSurface(surface);
    rental.setDescription(description);
    rental.setPicture(file.getOriginalFilename());
    System.out.println("Picture : " + file.getOriginalFilename());
    System.out.println(rental.toString());
    return ResponseEntity.ok(new MessageRentalResponseDTO("Rental created ! : "));
  }

  @PutMapping("/{id}")
  public ResponseEntity<MessageRentalResponseDTO> updateRental(@PathVariable Long id,
                                                               @RequestParam String name,
                                                               @RequestParam BigDecimal price,
                                                               @RequestParam BigDecimal surface,
                                                               @RequestParam String description) {
    if (id == 1) {
      PutRentalRequestDTO rental = new PutRentalRequestDTO();
      rental.setName(name);
      rental.setPrice(price);
      rental.setSurface(surface);
      rental.setDescription(description);
      System.out.println("Rental request :" + rental.toString());
      return ResponseEntity.ok(new MessageRentalResponseDTO(" rental updated "));

    } else {
      return ResponseEntity.notFound().build();
    }


  }

}
