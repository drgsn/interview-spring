package com.frieparking.userprofile;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
    return null;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
      @RequestBody UserDTO userDTO) {
    return null;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    return null;
  }

  @PostMapping("/{userId}/friends/{friendId}")
  public ResponseEntity<UserDTO> addFriend(@PathVariable Long userId,
      @PathVariable Long friendId) {
    return null;
  }

  @DeleteMapping("/{userId}/friends/{friendId}")
  public ResponseEntity<UserDTO> removeFriend(@PathVariable Long userId,
      @PathVariable Long friendId) {
    return null;
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<List<UserDTO>> getUserFriends(@PathVariable Long userId) {
    return null;
  }

  // Exception handling
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
    ErrorResponse error = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        ex.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
    ErrorResponse error = new ErrorResponse(
        HttpStatus.CONFLICT.value(),
        ex.getMessage(),
        LocalDateTime.now()
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
