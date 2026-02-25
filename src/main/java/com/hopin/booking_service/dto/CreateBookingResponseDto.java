package com.hopin.booking_service.dto;

import com.hopin.commonservice.models.BookingStatus;
import com.hopin.commonservice.models.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResponseDto {

    private Long bookingId;
    private BookingStatus bookingStatus;
    private Driver driver;

}
