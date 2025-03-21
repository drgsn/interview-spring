package com.frieparking.userprofile;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<UserDTO> method1(@RequestBody UserDTO userDTO) {
    return null;
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> method2(@PathVariable Long id) {
    return null;
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDTO> method3(@PathVariable Long id,
      @RequestBody UserDTO userDTO) {
    return null;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> method4(@PathVariable Long id) {
    return null;
  }

  @PostMapping("/{userId}/friends/{friendId}")
  public ResponseEntity<UserDTO> method5(@PathVariable Long userId,
      @PathVariable Long friendId) {
    return null;
  }

  @DeleteMapping("/{userId}/friends/{friendId}")
  public ResponseEntity<UserDTO> method6(@PathVariable Long userId,
      @PathVariable Long friendId) {
    return null;
  }

  @GetMapping("/{userId}/friends")
  public ResponseEntity<List<UserDTO>> method7(@PathVariable Long userId) {
    return null;
  }

}
