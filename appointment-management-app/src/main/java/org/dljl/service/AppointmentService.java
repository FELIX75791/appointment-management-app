package org.dljl.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.dljl.entity.*;
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
  public String createRecurringBlockInOneYear(CreateRecurringBlockInOneYearDto dto) {
    return apiClient.createRecurringBlockInOneYear(dto);
  }

  // Create Recurring Block
  public String createRecurringBlock(CreateRecurringBlockDto dto) {
    return apiClient.createRecurringBlock(dto);
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

  public Appointment updateAppointment(UpdateAppointmentDto dto) {
    return apiClient.updateAppointment(dto);
  }

  public String cancelAppointment(Long id) {
    return apiClient.cancelAppointment(id);
  }

  public Appointment getAppointmentById(Long id) {
    return apiClient.getAppointmentById(id);
  }

  public List<Appointment> getProviderAppointments(Long providerId) {
    return apiClient.getProviderAppointments(providerId);
  }

  public String deleteBlock(Long id) {
    return apiClient.deleteBlock(id);
  }
}