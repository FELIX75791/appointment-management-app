package org.dljl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.jsonwebtoken.JwtException;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

  private JwtService jwtService;

  @Mock private UserDetails userDetailsMock;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    jwtService = new JwtService();
  }

  @Test
  void testGenerateToken_Success() {
    String userName = "testUser";

    String token = jwtService.generateToken(userName);

    assertNotNull(token);
    assertDoesNotThrow(() -> jwtService.extractUsername(token));
  }

  @Test
  void testExtractUsername_Success() {
    String userName = "testUser";
    String token = jwtService.generateToken(userName);

    String extractedUsername = jwtService.extractUsername(token);

    assertEquals(userName, extractedUsername);
  }

  @Test
  void testExtractExpiration_Success() {
    String userName = "testUser";
    String token = jwtService.generateToken(userName);

    Date expiration = jwtService.extractExpiration(token);

    assertNotNull(expiration);
    assertTrue(expiration.after(new Date()));
  }

  @Test
  void testValidateToken_Success() {
    String userName = "testUser";
    String token = jwtService.generateToken(userName);

    when(userDetailsMock.getUsername()).thenReturn(userName);

    Boolean isValid = jwtService.validateToken(token, userDetailsMock);

    assertTrue(isValid);
    verify(userDetailsMock, times(1)).getUsername();
  }

  @Test
  void testValidateToken_InvalidUsername() {
    String userName = "testUser";
    String token = jwtService.generateToken(userName);

    when(userDetailsMock.getUsername()).thenReturn("wrongUser");

    Boolean isValid = jwtService.validateToken(token, userDetailsMock);

    assertFalse(isValid);
    verify(userDetailsMock, times(1)).getUsername();
  }

  @Test
  void testExtractAllClaims_InvalidToken() {
    String invalidToken = "invalid.token.here";

    assertThrows(JwtException.class, () -> jwtService.extractUsername(invalidToken));
  }

  @Test
  void testIsTokenExpired_Success() {
    String userName = "testUser";
    String token = jwtService.generateToken(userName);

    Boolean isExpired = jwtService.validateToken(token, userDetailsMock);

    assertNotNull(isExpired);
    assertFalse(isExpired);
  }
}
