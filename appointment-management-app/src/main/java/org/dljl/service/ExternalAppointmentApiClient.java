package org.dljl.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.dljl.entity.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class ExternalAppointmentApiClient {

  private final WebClient webClient;

  public ExternalAppointmentApiClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder.baseUrl("https://coms4156-436119.ue.r.appspot.com/").build();
  }

  // Create Appointment
  public Appointment createAppointment(CreateAppointmentDto dto) {
    try {
      return webClient
              .post()
              .uri("/appointments/createAppointment")
              .bodyValue(dto)
              .retrieve()
              .bodyToMono(Appointment.class)
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  // Create Block
  public String createBlock(CreateBlockDto dto) {
    try {
      return webClient
              .post()
              .uri("/appointments/createBlock")
              .bodyValue(dto)
              .retrieve()
              .bodyToMono(String.class)
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  // Create Recurring Block In One Year
  public String createRecurringBlockInOneYear(CreateRecurringBlockInOneYearDto dto) {
    return webClient
        .post()
        .uri("/appointments/createRecurringBlockInOneYear")
        .bodyValue(dto)
        .retrieve()
        .bodyToMono(String.class)
        .block();
  }

  // Create Recurring Block
  public String createRecurringBlock(CreateRecurringBlockDto dto) {
    try {
      return webClient
              .post()
              .uri("/appointments/createRecurringBlock")
              .bodyValue(dto)
              .retrieve()
              .bodyToMono(String.class)
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public List<List<LocalTime>> getAvailableSlots(Long providerId, LocalDate date) {
    String formattedDate = date.format(DateTimeFormatter.ISO_DATE); // Format the date to ISO format
    try {
      return webClient
              .get()
              .uri(
                      uriBuilder ->
                              uriBuilder
                                      .path("/appointments/provider/{providerId}/available/date/{date}")
                                      .build(providerId, formattedDate)) // Pass the formatted date
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<List<List<LocalTime>>>() {})
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public List<Map<String, Object>> getProviderAppointmentsByDate(Long providerId, LocalDate date) {
    String formattedDate = date.format(DateTimeFormatter.ISO_DATE);
    try {
      return webClient
              .get()
              .uri(
                      uriBuilder ->
                              uriBuilder
                                      .path("/appointments/provider/{providerId}/date/{date}")
                                      .build(providerId, formattedDate)) // Pass the formatted date
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public List<Map<String, Object>> getAppointmentHistory(Long providerId, Long userId) {
    try {
      return webClient
              .get()
              .uri(
                      uriBuilder ->
                              uriBuilder
                                      .path("/appointments/history")
                                      .queryParam("provider_id", providerId) // Match backend parameter name
                                      .queryParam("user_id", userId) // Match backend parameter name
                                      .build())
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public Appointment updateAppointment(UpdateAppointmentDto dto) {
    try {
      return webClient
              .put()
              .uri("/appointments/update")
              .bodyValue(dto)
              .retrieve()
              .bodyToMono(Appointment.class)
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public String cancelAppointment(Long id) {
    try {
      return webClient
              .put()
              .uri("/appointments/cancel/{id}", id)
              .retrieve()
              .bodyToMono(String.class)
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public Appointment getAppointmentById(Long id) {
    try {
      return webClient
              .get()
              .uri("/appointments/{id}", id)
              .retrieve()
              .bodyToMono(Appointment.class)
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public List<Appointment> getProviderAppointments(Long providerId) {
    try {
      return webClient
              .get()
              .uri("/appointments/provider/{providerId}", providerId)
              .retrieve()
              .bodyToMono(new ParameterizedTypeReference<List<Appointment>>() {})
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }

  public String deleteBlock(Long id) {
    try {
      return webClient
              .delete()
              .uri("/appointments/deleteBlock/{id}", id)
              .retrieve()
              .bodyToMono(String.class)
              .block();
    } catch (WebClientResponseException ex) {
      throw new ExternalApiException((HttpStatus) ex.getStatusCode(), ex.getResponseBodyAsString());
    }
  }
}
