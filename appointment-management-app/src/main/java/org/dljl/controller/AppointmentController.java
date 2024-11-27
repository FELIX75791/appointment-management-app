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

  @Autowired private AppointmentService appointmentService;

  // Create Appointment (Admin Only)
  @PostMapping("/createAppointment")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Appointment> createAppointment(
      @RequestBody CreateAppointmentInputDto inputDto) {
    return new ResponseEntity<>(appointmentService.createAppointment(inputDto), HttpStatus.CREATED);
  }

  // Create Block (Admin Only)
  @PostMapping("/createBlock")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createBlock(@RequestBody CreateBlockInputDto inputDto) {
    return new ResponseEntity<>(appointmentService.createBlock(inputDto), HttpStatus.CREATED);
  }

  // Create Recurring Block In One Year (Admin Only)
  @PostMapping("/createRecurringBlockInOneYear")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createRecurringBlockInOneYear(
      @RequestBody CreateRecurringBlockInOneYearInputDto inputDto) {
    return new ResponseEntity<>(
        appointmentService.createRecurringBlockInOneYear(inputDto), HttpStatus.CREATED);
  }

  // Create Recurring Block (Admin Only)
  @PostMapping("/createRecurringBlock")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createRecurringBlock(
      @RequestBody CreateRecurringBlockInputDto inputDto) {
    return new ResponseEntity<>(
        appointmentService.createRecurringBlock(inputDto), HttpStatus.CREATED);
  }

  // Get Available Slots (User Only)
  @GetMapping("/provider/{providerName}/available/date/{date}")
  public ResponseEntity<List<List<LocalTime>>> getAvailableSlots(
      @PathVariable String providerName, @PathVariable LocalDate date) {
    return ResponseEntity.ok(appointmentService.getAvailableSlots(providerName, date));
  }

  // Get Provider Appointments by Date (User and Admin)
  @GetMapping("/provider/{providerName}/date/{date}")
  public ResponseEntity<List<Map<String, Object>>> getProviderAppointmentsByDate(
      @PathVariable String providerName, @PathVariable LocalDate date) {
    return ResponseEntity.ok(appointmentService.getProviderAppointmentsByDate(providerName, date));
  }

  // Get Appointment History (User Only)
  @GetMapping("/history")
  public ResponseEntity<List<Map<String, Object>>> getAppointmentHistory(
      @RequestParam String providerName, // Accept providerName
      @RequestParam String userName) {
    return ResponseEntity.ok(appointmentService.getAppointmentHistory(providerName, userName));
  }

  // Update Appointment (Admin Only)
  @PutMapping("/update")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<Appointment> updateAppointment(
      @RequestBody UpdateAppointmentInputDto inputDto) {
    return ResponseEntity.ok(appointmentService.updateAppointment(inputDto));
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
  @GetMapping("/provider/{providerName}")
  public ResponseEntity<List<Appointment>> getProviderAppointments(
      @PathVariable String providerName) {
    return ResponseEntity.ok(appointmentService.getProviderAppointments(providerName));
  }

  // Delete Block (Admin Only)
  @DeleteMapping("/deleteBlock/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> deleteBlock(@PathVariable Long id) {
    return ResponseEntity.ok(appointmentService.deleteBlock(id));
  }
}
