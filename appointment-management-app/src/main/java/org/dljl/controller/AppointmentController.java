package org.dljl.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.dljl.entity.*;
import org.dljl.service.AppointmentService;
import org.dljl.service.ExternalApiException;
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
  public ResponseEntity<?> createAppointment(
      @RequestBody CreateAppointmentInputDto inputDto) {
    try {
      return new ResponseEntity<>(appointmentService.createAppointment(inputDto), HttpStatus.CREATED);
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Update Appointment (Admin Only)
  @PutMapping("/update")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<?> updateAppointment(
          @RequestBody UpdateAppointmentInputDto inputDto) {
    try {
      return new ResponseEntity<>(appointmentService.updateAppointment(inputDto), HttpStatus.ACCEPTED);
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Create Block (Admin Only)
  @PostMapping("/createBlock")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> createBlock(@RequestBody CreateBlockInputDto inputDto) {
    try {
      return new ResponseEntity<>(appointmentService.createBlock(inputDto), HttpStatus.CREATED);
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
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
    try {
      return new ResponseEntity<>(
            appointmentService.createRecurringBlock(inputDto), HttpStatus.CREATED);
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Get Available Slots (User Only)
  @GetMapping("/provider/{providerName}/available/date/{date}")
  public ResponseEntity<?> getAvailableSlots(
      @PathVariable String providerName, @PathVariable LocalDate date) {
    try {
      return ResponseEntity.ok(appointmentService.getAvailableSlots(providerName, date));
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Get Provider Appointments by Date (User and Admin)
  @GetMapping("/provider/{providerName}/date/{date}")
  public ResponseEntity<?> getProviderAppointmentsByDate(
      @PathVariable String providerName, @PathVariable LocalDate date) {
    try {
      return ResponseEntity.ok(appointmentService.getProviderAppointmentsByDate(providerName, date));
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Get Appointment History (User Only)
  @GetMapping("/history")
  public ResponseEntity<?> getAppointmentHistory(
      @RequestParam String providerName, // Accept providerName
      @RequestParam String userName) {
    try {
      return ResponseEntity.ok(appointmentService.getAppointmentHistory(providerName, userName));
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Cancel Appointment (Admin Only)
  @PutMapping("/cancel/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(appointmentService.cancelAppointment(id));
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Get Appointment by ID (User and Admin)
  @GetMapping("/{id}")
  public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(appointmentService.getAppointmentById(id));
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Get Provider's Appointments (User and Admin)
  @GetMapping("/provider/{providerName}")
  public ResponseEntity<?> getProviderAppointments(
      @PathVariable String providerName) {
    try {
      return ResponseEntity.ok(appointmentService.getProviderAppointments(providerName));
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }

  // Delete Block (Admin Only)
  @DeleteMapping("/deleteBlock/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<String> deleteBlock(@PathVariable Long id) {
    try {
      return ResponseEntity.ok(appointmentService.deleteBlock(id));
    } catch (ExternalApiException ex) {
      return ResponseEntity.status(ex.getStatus()).body("Error: " + ex.getMessage());
    } catch (RuntimeException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
    }
  }
}
