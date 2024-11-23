package org.dljl.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.dljl.entity.*;
import org.dljl.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

  // Create Recurring Block In One Year (Admin Only)
  @PostMapping("/createRecurringBlockInOneYear")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createRecurringBlockInOneYear(
      @RequestBody CreateRecurringBlockInOneYearDto dto) {
    return new ResponseEntity<>(appointmentService.createRecurringBlockInOneYear(dto), HttpStatus.CREATED);
  }

  // Create Recurring Block (Admin Only)
  @PostMapping("/createRecurringBlock")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createRecurringBlock(
          @RequestBody CreateRecurringBlockDto dto) {
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

  // Update Appointment (Admin Only)
  @PutMapping("/update")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Appointment> updateAppointment(@RequestBody UpdateAppointmentDto dto) {
    return ResponseEntity.ok(appointmentService.updateAppointment(dto));
  }

  // Cancel Appointment (Admin Only)
  @PutMapping("/cancel/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
    return ResponseEntity.ok(appointmentService.cancelAppointment(id));
  }

  // Get Appointment by ID (User and Admin)
  @GetMapping("/{id}")
  public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
    return ResponseEntity.ok(appointmentService.getAppointmentById(id));
  }

  // Get Provider's Appointments (User and Admin)
  @GetMapping("/provider/{providerId}")
  public ResponseEntity<List<Appointment>> getProviderAppointments(@PathVariable Long providerId) {
    return ResponseEntity.ok(appointmentService.getProviderAppointments(providerId));
  }

  // Delete Block (Admin Only)
  @DeleteMapping("/deleteBlock/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> deleteBlock(@PathVariable Long id) {
    return ResponseEntity.ok(appointmentService.deleteBlock(id));
  }
}

