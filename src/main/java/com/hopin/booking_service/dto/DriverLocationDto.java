package com.hopin.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DriverLocationDto {
    private Long driverId;
    private double latitude;
    private double longitude;
}
