package org.dljl.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.dljl.entity.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalAppointmentApiClient {

  private final WebClient webClient;

  public ExternalAppointmentApiClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
  }

  // Create Appointment
  public Appointment createAppointment(CreateAppointmentDto dto) {
    return webClient.post().uri("/appointments/createAppointment").bodyValue(dto).retrieve()
        .bodyToMono(Appointment.class).block();
  }

  // Create Block
  public String createBlock(CreateBlockDto dto) {
    return webClient.post().uri("/appointments/createBlock").bodyValue(dto).retrieve()
        .bodyToMono(String.class).block();
  }

  // Create Recurring Block In One Year
  public String createRecurringBlockInOneYear(CreateRecurringBlockInOneYearDto dto) {
    return webClient.post().uri("/appointments/createRecurringBlockInOneYear").bodyValue(dto)
        .retrieve().bodyToMono(String.class).block();
  }

  // Create Recurring Block In One Year
  public String createRecurringBlock(CreateRecurringBlockDto dto) {
    return webClient.post().uri("/appointments/createRecurringBlock").bodyValue(dto)
            .retrieve().bodyToMono(String.class).block();
  }

  public List<List<LocalTime>> getAvailableSlots(Long providerId, LocalDate date) {
    String formattedDate = date.format(DateTimeFormatter.ISO_DATE); // Format the date to ISO format
    return webClient.get().uri(
            uriBuilder -> uriBuilder.path("/appointments/provider/{providerId}/available/date/{date}")
                .build(providerId, formattedDate)) // Pass the formatted date
        .retrieve().bodyToMono(new ParameterizedTypeReference<List<List<LocalTime>>>() {
        }).block();
  }

  public List<Map<String, Object>> getProviderAppointmentsByDate(Long providerId, LocalDate date) {
    String formattedDate = date.format(DateTimeFormatter.ISO_DATE);
    return webClient.get().uri(
                    uriBuilder -> uriBuilder.path("/appointments/provider/{providerId}/date/{date}")
                            .build(providerId, formattedDate)) // Pass the formatted date
            .retrieve().bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
            }).block();
  }

  public List<Map<String, Object>> getAppointmentHistory(Long providerId, Long userId) {
    return webClient.get().uri(uriBuilder -> uriBuilder.path("/appointments/history")
            .queryParam("provider_id", providerId) // Match backend parameter name
            .queryParam("user_id", userId)         // Match backend parameter name
            .build()).retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
        }).block();
  }

  public Appointment updateAppointment(UpdateAppointmentDto dto) {
    return webClient.put().uri("/appointments/update").bodyValue(dto).retrieve()
            .bodyToMono(Appointment.class).block();
  }

  public String cancelAppointment(Long id) {
    return webClient.put().uri("/appointments/cancel/{id}", id).retrieve()
            .bodyToMono(String.class).block();
  }

  public Appointment getAppointmentById(Long id) {
    return webClient.get().uri("/appointments/{id}", id).retrieve()
            .bodyToMono(Appointment.class).block();
  }

  public List<Appointment> getProviderAppointments(Long providerId) {
    return webClient.get()
            .uri("/appointments/provider/{providerId}", providerId)
            .retrieve().bodyToMono(new ParameterizedTypeReference<List<Appointment>>() {
            }).block();
  }

  public String deleteBlock(Long id) {
    return webClient.delete().uri("/appointments/deleteBlock/{id}", id).retrieve()
            .bodyToMono(String.class).block();
  }
}

