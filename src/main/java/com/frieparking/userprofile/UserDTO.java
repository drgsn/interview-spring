package com.frieparking.userprofile;

import java.util.List;

public class UserDTO {
  private Long id;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<Long> friendIds;

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Long> getFriendIds() {
    return friendIds;
  }

  public void setFriendIds(List<Long> friendIds) {
    this.friendIds = friendIds;
  }
}
