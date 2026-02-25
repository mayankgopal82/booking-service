package com.hopin.booking_service.dto;

import com.hopin.commonservice.models.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingRequestDto {

    private long driverId;
//    private BookingStatus status;
    private String status;

}
