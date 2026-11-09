package com.hopin.booking_service.dto;

import com.hopin.commonservice.models.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverSummaryDto {

    private Long driverId;
    private String name;
    private int bookingCount;
    private List<Booking> bookings;


}
