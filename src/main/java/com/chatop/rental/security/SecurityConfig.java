package com.chatop.rental.security;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  private final CustomAuthenticationProvider authenticationProvider;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf(csrf->csrf.disable())
        .cors(cors -> cors.configurationSource(new CorsConfigurationSource()
                                                {
                                                  @Override
                                                  public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                                    CorsConfiguration cors = new CorsConfiguration();
                                                    cors.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                                                    cors.setAllowedMethods(Collections.singletonList("*"));
                                                    cors.setAllowedHeaders(Collections.singletonList("*"));
                                                    cors.setExposedHeaders(Collections.singletonList("Authorization"));
                                                    return cors;
                                                  }
                                                }))
      .authorizeHttpRequests(requests -> requests.requestMatchers("/auth/register", "/auth/login").permitAll()
      .anyRequest().authenticated())
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();

  }


}
