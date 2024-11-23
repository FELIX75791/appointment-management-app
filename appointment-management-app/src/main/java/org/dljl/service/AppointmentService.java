package org.dljl.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.dljl.entity.Appointment;
import org.dljl.entity.CreateAppointmentDto;
import org.dljl.entity.CreateBlockDto;
import org.dljl.entity.CreateRecurringBlockInOneYearDto;
import org.dljl.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

  @Autowired
  private UserInfoRepository userInfoRepository;

  @Autowired
  private ExternalAppointmentApiClient apiClient;

  // Create Appointment
  public Appointment createAppointment(CreateAppointmentDto dto) {
    return apiClient.createAppointment(dto);
  }

  // Create Block
  public String createBlock(CreateBlockDto dto) {
    return apiClient.createBlock(dto);
  }

  // Create Recurring Block
  public String createRecurringBlock(CreateRecurringBlockInOneYearDto dto) {
    return apiClient.createRecurringBlockInOneYear(dto);
  }

  // Get Available Slots
  public List<List<LocalTime>> getAvailableSlots(String providerName, LocalDate date) {
    // Resolve providerName to providerId from the user database
    Long providerId = userInfoRepository.findByName(providerName)
        .orElseThrow(() -> new RuntimeException("Provider not found"))
        .getId();

    // Call the external API client with the providerId
    return apiClient.getAvailableSlots(providerId, date);
  }

  // Get Appointment History
  public List<Map<String, Object>> getAppointmentHistory(String providerName, Long userId) {
    // Resolve providerName to providerId from the user database
    Long providerId = userInfoRepository.findByName(providerName)
        .orElseThrow(() -> new RuntimeException("Provider not found"))
        .getId();

    // Call the external API client with the resolved providerId and userId
    return apiClient.getAppointmentHistory(providerId, userId);
  }
}