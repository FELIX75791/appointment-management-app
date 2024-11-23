package org.dljl.controller;

import org.dljl.entity.AuthRequest;
import org.dljl.entity.UserInfo;
import org.dljl.service.JwtService;
import org.dljl.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {

  @Autowired
  private UserInfoService service;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @GetMapping("/welcome")
  public String welcome() {
    return "Welcome this endpoint is not secure";
  }

  @PostMapping("/addNewUser")
  public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
    try {
      String response = service.addUser(userInfo);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.badRequest().body("Error: " + ex.getMessage());
    }
  }

  @GetMapping("/user/userProfile")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public String userProfile() {
    return "Welcome to User Profile";
  }

  @GetMapping("/admin/adminProfile")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public String adminProfile() {
    return "Welcome to Admin Profile";
  }

  @PostMapping("/generateToken")
  public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
            authRequest.getPassword())
    );
    if (authentication.isAuthenticated()) {
      return jwtService.generateToken(authRequest.getUsername());
    } else {
      throw new UsernameNotFoundException("Invalid user request!");
    }
  }
}
