package org.dljl.service;

import java.util.Optional;
import org.dljl.entity.UserInfo;
import org.dljl.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

  @Autowired
  private UserInfoRepository repository;

  @Autowired
  private PasswordEncoder encoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserInfo> userDetail = repository.findByEmail(
        username); // Assuming 'email' is used as username

    // Converting UserInfo to UserDetails
    return userDetail.map(UserInfoDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  public String addUser(UserInfo userInfo) {

    if (repository.existsByName(userInfo.getName())) {
      throw new IllegalArgumentException("Name already exists: " + userInfo.getName());
    }

    if (repository.existsByEmail(userInfo.getEmail())) {
      throw new IllegalArgumentException("Email already exists: " + userInfo.getEmail());
    }

    // Generate a fallback ID if not provided
    if (userInfo.getId() == null) {
      userInfo.setId(generateNewId());
    }

    // Check for duplicate ID
    if (repository.existsById(userInfo.getId())) {
      throw new IllegalArgumentException("User with the provided ID already exists.");
    }

    // Encode password before saving
    userInfo.setPassword(encoder.encode(userInfo.getPassword()));

    // Save user to the repository
    repository.save(userInfo);
    return "User Added Successfully";
  }

  private Long generateNewId() {
    long timestamp = System.currentTimeMillis(); // Current time in milliseconds
    long randomPart = (long) (Math.random() * 1_000_000); // Random number between 0 and 999999
    return timestamp * 1_000_000 + randomPart; // Combine timestamp and random number
  }
}