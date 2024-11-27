package org.dljl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.util.Optional;
import org.dljl.entity.UserInfo;
import org.dljl.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserInfoServiceTest {

  private UserInfoService userInfoService;

  @Mock private UserInfoRepository userInfoRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    userInfoService = new UserInfoService();

    // Use reflection to set private fields
    Field repositoryField = UserInfoService.class.getDeclaredField("repository");
    repositoryField.setAccessible(true);
    repositoryField.set(userInfoService, userInfoRepository);

    Field encoderField = UserInfoService.class.getDeclaredField("encoder");
    encoderField.setAccessible(true);
    encoderField.set(userInfoService, passwordEncoder);
  }

  @Test
  void testLoadUserByUsername_Success() {
    String email = "test@example.com";
    UserInfo userInfo = new UserInfo(1L, "Test User", email, "password123", "ROLE_USER");

    when(userInfoRepository.findByEmail(email)).thenReturn(Optional.of(userInfo));

    UserDetails userDetails = userInfoService.loadUserByUsername(email);

    assertNotNull(userDetails);
    assertEquals(email, userDetails.getUsername());
    assertEquals("password123", userDetails.getPassword());
    assertTrue(
        userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
  }

  @Test
  void testLoadUserByUsername_NotFound() {
    String email = "test@example.com";

    when(userInfoRepository.findByEmail(email)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> userInfoService.loadUserByUsername(email));
  }

  @Test
  void testAddUser_Success() {
    UserInfo userInfo =
        new UserInfo(null, "Test User", "test@example.com", "password123", "ROLE_USER");
    String encodedPassword = "encodedPassword";

    when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
    when(userInfoRepository.existsById(anyLong())).thenReturn(false);

    String result = userInfoService.addUser(userInfo);

    assertEquals("User Added Successfully", result);

    ArgumentCaptor<UserInfo> userInfoCaptor = ArgumentCaptor.forClass(UserInfo.class);
    verify(userInfoRepository).save(userInfoCaptor.capture());

    UserInfo savedUserInfo = userInfoCaptor.getValue();
    assertNotNull(savedUserInfo.getId()); // ID is generated
    assertEquals(encodedPassword, savedUserInfo.getPassword());
  }

  @Test
  void testAddUser_DuplicateId() {
    UserInfo userInfo =
        new UserInfo(1L, "Test User", "test@example.com", "password123", "ROLE_USER");

    when(userInfoRepository.existsById(1L)).thenReturn(true);

    assertThrows(IllegalArgumentException.class, () -> userInfoService.addUser(userInfo));
  }

  @Test
  void testAddUser_GeneratesId() {
    UserInfo userInfo =
        new UserInfo(null, "Test User", "test@example.com", "password123", "ROLE_USER");
    String encodedPassword = "encodedPassword";

    when(passwordEncoder.encode("password123")).thenReturn(encodedPassword);
    when(userInfoRepository.existsById(anyLong())).thenReturn(false);

    String result = userInfoService.addUser(userInfo);

    assertEquals("User Added Successfully", result);

    assertNotNull(userInfo.getId());
  }
}
