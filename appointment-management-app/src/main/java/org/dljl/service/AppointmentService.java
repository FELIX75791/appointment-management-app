package org.dljl.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
  public Appointment createAppointment(CreateAppointmentInputDto inputDto) {
    String providerName = inputDto.getProviderName();
    String userName = inputDto.getUserName();

    Long providerId = userInfoRepository.findByName(providerName)
            .orElseThrow(() -> new RuntimeException("Provider not found"))
            .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
        throw new RuntimeException("Please enter a provider name");
    }

    Long userId = userInfoRepository.findByName(userName)
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();

    CreateAppointmentDto dto = new CreateAppointmentDto();
    dto.setProviderId(providerId);
    dto.setUserId(userId);
    dto.setStartDateTime(inputDto.getStartDateTime());
    dto.setEndDateTime(inputDto.getEndDateTime());
    dto.setStatus(inputDto.getStatus());
    dto.setServiceType(inputDto.getServiceType());
    dto.setComments(inputDto.getComments());
    return apiClient.createAppointment(dto);
  }

  // Create Block
  public String createBlock(CreateBlockInputDto inputDto) {
    String providerName = inputDto.getProviderName();

    Long providerId = userInfoRepository.findByName(providerName)
            .orElseThrow(() -> new RuntimeException("Provider not found"))
            .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
      throw new RuntimeException("Please enter a provider name");
    }

    CreateBlockDto dto = new CreateBlockDto();
    dto.setProviderId(providerId);
    dto.setStartDateTime(inputDto.getStartDateTime());
    dto.setEndDateTime(inputDto.getEndDateTime());
    return apiClient.createBlock(dto);
  }

  // Create Recurring Block In One Year
  public String createRecurringBlockInOneYear(CreateRecurringBlockInOneYearInputDto inputDto) {
    String providerName = inputDto.getProviderName();

    Long providerId = userInfoRepository.findByName(providerName)
            .orElseThrow(() -> new RuntimeException("Provider not found"))
            .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
      throw new RuntimeException("Please enter a provider name");
    }

    CreateRecurringBlockInOneYearDto dto = new CreateRecurringBlockInOneYearDto();
    dto.setProviderId(providerId);
    dto.setStartTime(inputDto.getStartTime());
    dto.setEndTime(inputDto.getEndTime());
    return apiClient.createRecurringBlockInOneYear(dto);
  }

  // Create Recurring Block
  public String createRecurringBlock(CreateRecurringBlockInputDto inputDto) {
    String providerName = inputDto.getProviderName();

    Long providerId = userInfoRepository.findByName(providerName)
            .orElseThrow(() -> new RuntimeException("Provider not found"))
            .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
      throw new RuntimeException("Please enter a provider name");
    }

    CreateRecurringBlockDto dto = new CreateRecurringBlockDto();
    dto.setProviderId(providerId);
    dto.setStartDate(inputDto.getStartDate());
    dto.setEndDate(inputDto.getEndDate());
    dto.setStartTime(inputDto.getStartTime());
    dto.setEndTime(inputDto.getEndTime());
    return apiClient.createRecurringBlock(dto);
  }

  // Get Available Slots
  public List<List<LocalTime>> getAvailableSlots(String providerName, LocalDate date) {
    // Resolve providerName to providerId from the user database
    Long providerId = userInfoRepository.findByName(providerName)
        .orElseThrow(() -> new RuntimeException("Provider not found"))
        .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
      throw new RuntimeException("Please enter a provider name");
    }

    // Call the external API client with the providerId
    return apiClient.getAvailableSlots(providerId, date);
  }


  // Get a Provider's Appointments By Date
  public List<Map<String, Object>> getProviderAppointmentsByDate(String providerName, LocalDate date) {
    // Resolve providerName to providerId from the user database
    Long providerId = userInfoRepository.findByName(providerName)
            .orElseThrow(() -> new RuntimeException("Provider not found"))
            .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
      throw new RuntimeException("Please enter a provider name");
    }

    // Call the external API client with the resolved providerId and userId
    return apiClient.getProviderAppointmentsByDate(providerId, date);
  }


  // Get Appointment History
  public List<Map<String, Object>> getAppointmentHistory(String providerName, String userName) {
    // Resolve providerName to providerId from the user database
    Long providerId = userInfoRepository.findByName(providerName)
            .orElseThrow(() -> new RuntimeException("Provider not found"))
            .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
      throw new RuntimeException("Please enter a provider name");
    }

    Long userId = userInfoRepository.findByName(userName)
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();

    // Call the external API client with the resolved providerId and userId
    return apiClient.getAppointmentHistory(providerId, userId);
  }

  public Appointment updateAppointment(UpdateAppointmentInputDto inputDto) {
    String userName = inputDto.getUserName();

    Long userId = userInfoRepository.findByName(userName)
            .orElseThrow(() -> new RuntimeException("User not found"))
            .getId();

    UpdateAppointmentDto dto = new UpdateAppointmentDto();
    dto.setUserId(userId);
    dto.setStartDateTime(inputDto.getStartDateTime());
    dto.setEndDateTime(inputDto.getEndDateTime());
    dto.setStatus(inputDto.getStatus());
    dto.setServiceType(inputDto.getServiceType());
    dto.setComments(inputDto.getComments());
    return apiClient.updateAppointment(dto);
  }

  public String cancelAppointment(Long id) {
    return apiClient.cancelAppointment(id);
  }

  public Appointment getAppointmentById(Long id) {
    return apiClient.getAppointmentById(id);
  }

  public List<Appointment> getProviderAppointments(String providerName) {
    Long providerId = userInfoRepository.findByName(providerName)
            .orElseThrow(() -> new RuntimeException("Provider not found"))
            .getId();

    if (!Objects.equals(userInfoRepository.findByName(providerName).get().getRoles(), "ROLE_ADMIN"))
    {
      throw new RuntimeException("Please enter a provider name");
    }

    return apiClient.getProviderAppointments(providerId);
  }

  public String deleteBlock(Long id) {
    return apiClient.deleteBlock(id);
  }
}