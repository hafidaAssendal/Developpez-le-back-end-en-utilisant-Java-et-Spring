package com.chatop.rental.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.access.AccessDeniedException;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

  //  ResponseStatusException
  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<?> handleResponseStatusException(ResponseStatusException ex) {
    return ResponseEntity
      .status(ex.getStatusCode())
      .body(Map.of("error", ex.getReason()));
  }

  // 400
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(Map.of("error", "Bad request: " + ex.getMessage()));
  }

  // "not found" -> 404
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<?> handleRuntime(RuntimeException ex) {
    if (ex.getMessage() != null && ex.getMessage().toLowerCase().contains("not found")) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Map.of("error", ex.getMessage()));
    }
    // 500 erreur interne
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(Map.of("error", "Internal server error: " + ex.getMessage()));
  }
  // 403
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
    return ResponseEntity
      .status(HttpStatus.FORBIDDEN)
      .body(Map.of("error", "Access denied: " + ex.getMessage()));
  }

  // exceptions 500
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGlobal(Exception ex) {
    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(Map.of("error", "Internal server error: " + ex.getMessage()));
  }
}
