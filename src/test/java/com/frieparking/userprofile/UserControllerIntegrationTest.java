package com.frieparking.userprofile;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest")
      .withDatabaseName("testdb")
      .withUsername("test")
      .withPassword("test");

  @LocalServerPort
  private Integer port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EntityManager entityManager;

  private String baseUrl;

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeEach
  void setUp() {
    baseUrl = "http://localhost:" + port + "/api/users";
    userRepository.deleteAll();
    entityManager.clear();
  }

  @Test
  void createUser_Success() {
    UserDTO userDTO = new UserDTO();
    userDTO.setFirstName("John");
    userDTO.setLastName("Doe");
    userDTO.setEmail("john@example.com");
    userDTO.setPassword("password123");

    ResponseEntity<UserDTO> response = restTemplate.postForEntity(
        baseUrl, userDTO, UserDTO.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().getEmail()).isEqualTo("john@example.com");
    assertThat(response.getBody().getFirstName()).isEqualTo("John");

    Optional<User> savedUser = userRepository.findByEmail("john@example.com");
    assertThat(savedUser).isPresent();
    assertThat(savedUser.get().getFirstName()).isEqualTo("John");
    assertThat(passwordEncoder.matches("password123", savedUser.get().getPassword())).isTrue();
  }

  @Test
  void getUserFriends_Success() {
    User user1 = createAndSaveUser("user1@example.com", "User", "One");
    User user2 = createAndSaveUser("user2@example.com", "User", "Two");
    User user3 = createAndSaveUser("user3@example.com", "User", "Three");

    user1.getFriends().add(user2);
    user1.getFriends().add(user3);
    userRepository.save(user1);

    ResponseEntity<List<UserDTO>> response = restTemplate.exchange(
        baseUrl + "/{userId}/friends",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<UserDTO>>() {},
        user1.getId()
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody()).hasSize(2);
    assertThat(response.getBody().stream().map(UserDTO::getEmail))
        .containsExactlyInAnyOrder("user2@example.com", "user3@example.com");
  }

  private User createAndSaveUser(String email, String firstName, String lastName) {
    User user = new User();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPassword(passwordEncoder.encode("password"));
    return userRepository.save(user);
  }
}
