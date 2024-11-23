package org.dljl.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.dljl.entity.Appointment;
import org.dljl.entity.CreateAppointmentDto;
import org.dljl.entity.CreateBlockDto;
import org.dljl.entity.CreateRecurringBlockInOneYearDto;
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

  public List<List<LocalTime>> getAvailableSlots(Long providerId, LocalDate date) {
    String formattedDate = date.format(DateTimeFormatter.ISO_DATE); // Format the date to ISO format
    return webClient.get().uri(
            uriBuilder -> uriBuilder.path("/appointments/provider/{providerId}/available/date/{date}")
                .build(providerId, formattedDate)) // Pass the formatted date
        .retrieve().bodyToMono(new ParameterizedTypeReference<List<List<LocalTime>>>() {
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
}

