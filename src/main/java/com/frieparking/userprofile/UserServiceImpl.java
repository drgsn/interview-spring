package com.frieparking.userprofile;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public UserDTO createUser(UserDTO userDTO) {
    return null;
  }

  @Override
  public UserDTO getUserById(Long id) {
    return null;
  }

  @Override
  public UserDTO updateUser(Long id, UserDTO userDTO) {
    return null;
  }

  @Override
  public void deleteUser(Long id) {
  }

  @Override
  public UserDTO addFriend(Long userId, Long friendId) {
    return null;
  }

  @Override
  public UserDTO removeFriend(Long userId, Long friendId) {
    return null;
  }

  @Override
  public List<UserDTO> getAllFriends(Long userId) {
    return null;
  }

  private UserDTO convertToDTO(User user) {
    UserDTO dto = new UserDTO();
    dto.setId(user.getId());
    dto.setFirstName(user.getFirstName());
    dto.setLastName(user.getLastName());
    dto.setEmail(user.getEmail());
    dto.setFriendIds(user.getFriends().stream()
        .map(User::getId)
        .toList());
    return dto;
  }
}
