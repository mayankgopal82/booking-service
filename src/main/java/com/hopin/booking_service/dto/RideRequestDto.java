package com.hopin.booking_service.dto;
import com.hopin.commonservice.models.ExactLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {


    private long passengerId;
    private ExactLocation startLocation;
    private ExactLocation endLocation;
    private List<Long> driverIds;
    private long bookingId;

}
