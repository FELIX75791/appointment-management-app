package org.dljl.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBlockDto {

  private Long providerId;
  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
}
