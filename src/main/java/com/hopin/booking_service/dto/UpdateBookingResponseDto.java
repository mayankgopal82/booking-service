package com.hopin.booking_service.dto;

import com.hopin.commonservice.models.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateBookingResponseDto {
    private long driverId;
    private BookingStatus status;
}
