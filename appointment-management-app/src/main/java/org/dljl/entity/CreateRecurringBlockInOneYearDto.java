package org.dljl.entity;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecurringBlockInOneYearDto {

  private Long providerId;
  private LocalTime startTime;
  private LocalTime endTime;
}
