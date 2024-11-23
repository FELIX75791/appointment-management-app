package org.dljl.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentInputDto {

    private String providerName;
    private String userName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String status;
    private String serviceType;
    private String comments;
}
