package com.chatop.rental.security;

import com.chatop.rental.entites.User;
import com.chatop.rental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserService userService;
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Optional<User> user = userService.getUserByEmail(email);

    if (user.isEmpty())throw new UsernameNotFoundException("User not found");

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_USER")); // Par défaut


    return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),true,true,true,true, authorities);

  }

}
