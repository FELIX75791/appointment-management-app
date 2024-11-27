package org.dljl.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRecurringBlockInputDto {

  private String providerName;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDate startDate;
  private LocalDate endDate;
}
