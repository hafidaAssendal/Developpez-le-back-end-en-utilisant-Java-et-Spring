package com.chatop.rental.security;

import com.chatop.rental.entites.User;
import com.chatop.rental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AuthenticatedUser {

  @Autowired
  private UserService userService;

  public User get() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails userDetails) {
      String email = userDetails.getUsername();
      return userService.getUserByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "NOT FOUND USER"));
    }

    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED USER  ");
  }

}
