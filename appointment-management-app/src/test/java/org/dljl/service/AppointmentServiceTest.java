package org.dljl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.dljl.entity.*;
import org.dljl.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AppointmentServiceTest {

  @Mock
  private UserInfoRepository userInfoRepository;

  @Mock
  private ExternalAppointmentApiClient apiClient;

  @InjectMocks
  private AppointmentService appointmentService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCreateAppointment_Success() {
    CreateAppointmentInputDto inputDto = new CreateAppointmentInputDto(
        "provider1", "user1", LocalDateTime.now(), LocalDateTime.now().plusHours(1),
        "Confirmed", "Consultation", "No comments");

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");
    UserInfo user = new UserInfo(2L, "user1", "user@example.com", "password", "ROLE_USER");

    when(userInfoRepository.findByName("provider1")).thenReturn(Optional.of(provider));
    when(userInfoRepository.findByName("user1")).thenReturn(Optional.of(user));

    Appointment expectedAppointment = new Appointment();
    when(apiClient.createAppointment(any(CreateAppointmentDto.class))).thenReturn(expectedAppointment);

    Appointment result = appointmentService.createAppointment(inputDto);

    assertNotNull(result);
    verify(apiClient, times(1)).createAppointment(any(CreateAppointmentDto.class));
  }

  @Test
  void testCreateAppointment_ProviderNotFound() {
    CreateAppointmentInputDto inputDto = new CreateAppointmentInputDto();
    inputDto.setProviderName("provider1");

    when(userInfoRepository.findByName("provider1")).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> appointmentService.createAppointment(inputDto));

    assertEquals("Provider not found", exception.getMessage());
  }

  @Test
  void testCreateAppointment_UserNotFound() {
    CreateAppointmentInputDto inputDto = new CreateAppointmentInputDto();
    inputDto.setProviderName("provider1");
    inputDto.setUserName("user1");

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");
    when(userInfoRepository.findByName("provider1")).thenReturn(Optional.of(provider));
    when(userInfoRepository.findByName("user1")).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> appointmentService.createAppointment(inputDto));

    assertEquals("User not found", exception.getMessage());
  }

  @Test
  void testCreateBlock_Success() {
    CreateBlockInputDto inputDto = new CreateBlockInputDto("provider1", LocalDateTime.now(), LocalDateTime.now().plusHours(2));

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");

    when(userInfoRepository.findByName("provider1")).thenReturn(Optional.of(provider));
    when(apiClient.createBlock(any(CreateBlockDto.class))).thenReturn("Block Created");

    String result = appointmentService.createBlock(inputDto);

    assertEquals("Block Created", result);
    verify(apiClient, times(1)).createBlock(any(CreateBlockDto.class));
  }

  @Test
  void testCreateRecurringBlockInOneYear_Success() {
    CreateRecurringBlockInOneYearInputDto inputDto = new CreateRecurringBlockInOneYearInputDto(
        "provider1", LocalTime.of(9, 0), LocalTime.of(10, 0));

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");

    when(userInfoRepository.findByName("provider1")).thenReturn(Optional.of(provider));
    when(apiClient.createRecurringBlockInOneYear(any(CreateRecurringBlockInOneYearDto.class)))
        .thenReturn("Recurring Block Created");

    String result = appointmentService.createRecurringBlockInOneYear(inputDto);

    assertEquals("Recurring Block Created", result);
    verify(apiClient, times(1)).createRecurringBlockInOneYear(any(CreateRecurringBlockInOneYearDto.class));
  }

  @Test
  void testGetAvailableSlots_Success() {
    String providerName = "provider1";
    LocalDate date = LocalDate.now();

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");

    when(userInfoRepository.findByName(providerName)).thenReturn(Optional.of(provider));

    List<List<LocalTime>> expectedSlots = List.of(List.of(LocalTime.of(10, 0), LocalTime.of(11, 0)));
    when(apiClient.getAvailableSlots(1L, date)).thenReturn(expectedSlots);

    List<List<LocalTime>> result = appointmentService.getAvailableSlots(providerName, date);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(apiClient, times(1)).getAvailableSlots(1L, date);
  }

  @Test
  void testGetProviderAppointmentsByDate_Success() {
    String providerName = "provider1";
    LocalDate date = LocalDate.now();

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");

    when(userInfoRepository.findByName(providerName)).thenReturn(Optional.of(provider));

    List<Map<String, Object>> appointments = List.of(Map.of("id", 1L, "date", date));
    when(apiClient.getProviderAppointmentsByDate(1L, date)).thenReturn(appointments);

    List<Map<String, Object>> result = appointmentService.getProviderAppointmentsByDate(providerName, date);

    assertNotNull(result);
    assertEquals(1, result.size());
    verify(apiClient, times(1)).getProviderAppointmentsByDate(1L, date);
  }

  @Test
  void testUpdateAppointment_Success() {
    UpdateAppointmentInputDto inputDto = new UpdateAppointmentInputDto();
    inputDto.setUserName("user1");
    inputDto.setAppointmentId(1L);
    inputDto.setStartDateTime(LocalDateTime.now());
    inputDto.setEndDateTime(LocalDateTime.now().plusHours(1));
    inputDto.setStatus("Updated");
    inputDto.setServiceType("Consultation");
    inputDto.setComments("Updated comments");

    UserInfo user = new UserInfo(2L, "user1", "user@example.com", "password", "ROLE_USER");

    when(userInfoRepository.findByName("user1")).thenReturn(Optional.of(user));

    Appointment expectedAppointment = new Appointment();
    when(apiClient.updateAppointment(any(UpdateAppointmentDto.class))).thenReturn(expectedAppointment);

    Appointment result = appointmentService.updateAppointment(inputDto);

    assertNotNull(result);
    verify(apiClient, times(1)).updateAppointment(any(UpdateAppointmentDto.class));
  }

  @Test
  void testCancelAppointment_Success() {
    Long appointmentId = 1L;

    when(apiClient.cancelAppointment(appointmentId)).thenReturn("Cancelled");

    String result = appointmentService.cancelAppointment(appointmentId);

    assertEquals("Cancelled", result);
    verify(apiClient, times(1)).cancelAppointment(appointmentId);
  }

  @Test
  void testGetAppointmentById_Success() {
    Long appointmentId = 1L;

    Appointment expectedAppointment = new Appointment();
    expectedAppointment.setAppointmentId(appointmentId);

    when(apiClient.getAppointmentById(appointmentId)).thenReturn(expectedAppointment);

    Appointment result = appointmentService.getAppointmentById(appointmentId);

    assertNotNull(result);
    assertEquals(appointmentId, result.getAppointmentId());
  }

  @Test
  void testDeleteBlock_Success() {
    Long blockId = 1L;

    when(apiClient.deleteBlock(blockId)).thenReturn("Block Deleted");

    String result = appointmentService.deleteBlock(blockId);

    assertEquals("Block Deleted", result);
    verify(apiClient, times(1)).deleteBlock(blockId);
  }

  @Test
  void testCreateRecurringBlock_Success() {
    CreateRecurringBlockInputDto inputDto = new CreateRecurringBlockInputDto();
    inputDto.setProviderName("provider1");
    inputDto.setStartDate(LocalDate.now());
    inputDto.setEndDate(LocalDate.now().plusDays(7));
    inputDto.setStartTime(LocalTime.of(9, 0));
    inputDto.setEndTime(LocalTime.of(10, 0));

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");

    when(userInfoRepository.findByName("provider1")).thenReturn(Optional.of(provider));

    CreateRecurringBlockDto dto = new CreateRecurringBlockDto();
    dto.setProviderId(1L);
    dto.setStartDate(LocalDate.now());
    dto.setEndDate(LocalDate.now().plusDays(7));
    dto.setStartTime(LocalTime.of(9, 0));
    dto.setEndTime(LocalTime.of(10, 0));

    when(apiClient.createRecurringBlock(any(CreateRecurringBlockDto.class))).thenReturn("Recurring Block Created");

    String result = appointmentService.createRecurringBlock(inputDto);

    assertEquals("Recurring Block Created", result);
    verify(apiClient, times(1)).createRecurringBlock(any(CreateRecurringBlockDto.class));
  }

  @Test
  void testCreateRecurringBlock_ProviderNotFound() {
    CreateRecurringBlockInputDto inputDto = new CreateRecurringBlockInputDto();
    inputDto.setProviderName("provider1");

    when(userInfoRepository.findByName("provider1")).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> appointmentService.createRecurringBlock(inputDto));

    assertEquals("Provider not found", exception.getMessage());
  }

  @Test
  void testGetAppointmentHistory_Success() {
    String providerName = "provider1";
    String userName = "user1";

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");
    UserInfo user = new UserInfo(2L, "user1", "user@example.com", "password", "ROLE_USER");

    when(userInfoRepository.findByName(providerName)).thenReturn(Optional.of(provider));
    when(userInfoRepository.findByName(userName)).thenReturn(Optional.of(user));

    List<Map<String, Object>> history = List.of(Map.of("appointmentId", 1L, "status", "Completed"));
    when(apiClient.getAppointmentHistory(1L, 2L)).thenReturn(history);

    List<Map<String, Object>> result = appointmentService.getAppointmentHistory(providerName, userName);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Completed", result.get(0).get("status"));
    verify(apiClient, times(1)).getAppointmentHistory(1L, 2L);
  }

  @Test
  void testGetAppointmentHistory_UserNotFound() {
    String providerName = "provider1";
    String userName = "user1";

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");

    when(userInfoRepository.findByName(providerName)).thenReturn(Optional.of(provider));
    when(userInfoRepository.findByName(userName)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> appointmentService.getAppointmentHistory(providerName, userName));

    assertEquals("User not found", exception.getMessage());
  }

  @Test
  void testGetProviderAppointments_Success() {
    String providerName = "provider1";

    UserInfo provider = new UserInfo(1L, "provider1", "provider@example.com", "password", "ROLE_ADMIN");

    when(userInfoRepository.findByName(providerName)).thenReturn(Optional.of(provider));

    List<Appointment> appointments = List.of(new Appointment(1L, 1L, 2L, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Confirmed", "Consultation", "No comments"));
    when(apiClient.getProviderAppointments(1L)).thenReturn(appointments);

    List<Appointment> result = appointmentService.getProviderAppointments(providerName);

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Confirmed", result.get(0).getStatus());
    verify(apiClient, times(1)).getProviderAppointments(1L);
  }

  @Test
  void testGetProviderAppointments_ProviderNotFound() {
    String providerName = "provider1";

    when(userInfoRepository.findByName(providerName)).thenReturn(Optional.empty());

    Exception exception = assertThrows(RuntimeException.class, () -> appointmentService.getProviderAppointments(providerName));

    assertEquals("Provider not found", exception.getMessage());
  }

}
