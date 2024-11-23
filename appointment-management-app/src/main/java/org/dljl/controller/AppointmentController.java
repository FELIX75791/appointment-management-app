package org.dljl.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.dljl.entity.Appointment;
import org.dljl.entity.CreateAppointmentDto;
import org.dljl.entity.CreateBlockDto;
import org.dljl.entity.CreateRecurringBlockInOneYearDto;
import org.dljl.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

  @Autowired
  private AppointmentService appointmentService;

  // Create Appointment (Admin Only)
  @PostMapping("/createAppointment")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Appointment> createAppointment(@RequestBody CreateAppointmentDto dto) {
    return new ResponseEntity<>(appointmentService.createAppointment(dto), HttpStatus.CREATED);
  }

  // Create Block (Admin Only)
  @PostMapping("/createBlock")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createBlock(@RequestBody CreateBlockDto dto) {
    return new ResponseEntity<>(appointmentService.createBlock(dto), HttpStatus.CREATED);
  }

  // Create Recurring Block (Admin Only)
  @PostMapping("/createRecurringBlockInOneYear")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createRecurringBlock(
      @RequestBody CreateRecurringBlockInOneYearDto dto) {
    return new ResponseEntity<>(appointmentService.createRecurringBlock(dto), HttpStatus.CREATED);
  }

  // Get Available Slots (User Only)
  @GetMapping("/provider/{providerName}/available/date/{date}")
  public ResponseEntity<List<List<LocalTime>>> getAvailableSlots(
      @PathVariable String providerName,
      @PathVariable LocalDate date) {
    return ResponseEntity.ok(appointmentService.getAvailableSlots(providerName, date));
  }

  // Get Appointment History (User Only)
  @GetMapping("/history")
  public ResponseEntity<List<Map<String, Object>>> getAppointmentHistory(
      @RequestParam String providerName, // Accept providerName
      @RequestParam Long userId) {
    return ResponseEntity.ok(appointmentService.getAppointmentHistory(providerName, userId));
  }
}

