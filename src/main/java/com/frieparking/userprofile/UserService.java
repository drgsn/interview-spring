package com.frieparking.userprofile;

import java.util.List;

public interface UserService {

  UserDTO createUser(UserDTO userDTO);

  UserDTO getUserById(Long id);

  UserDTO updateUser(Long id, UserDTO userDTO);

  void deleteUser(Long id);

  UserDTO addFriend(Long userId, Long friendId);

  UserDTO removeFriend(Long userId, Long friendId);

  List<UserDTO> getAllFriends(Long userId);
}
