package org.dljl.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentDto {

  private Long appointmentId; // Required
  private Long userId; // Optional
  private LocalDateTime startDateTime; // Optional
  private LocalDateTime endDateTime; // Optional
  private String status; // Optional
  private String serviceType; // Optional
  private String comments; // Optional
}
