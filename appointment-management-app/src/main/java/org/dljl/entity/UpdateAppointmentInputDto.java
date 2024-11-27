package org.dljl.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAppointmentInputDto {

  private Long appointmentId; // Required
  private String userName; // Optional
  private LocalDateTime startDateTime; // Optional
  private LocalDateTime endDateTime; // Optional
  private String status; // Optional
  private String serviceType; // Optional
  private String comments; // Optional
}
