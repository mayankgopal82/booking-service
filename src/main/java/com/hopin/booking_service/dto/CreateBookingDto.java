package com.hopin.booking_service.dto;


import com.hopin.commonservice.models.ExactLocation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingDto {

    private long passengerId;
    private  ExactLocation startLocation;
    private ExactLocation endLocation;

    public ExactLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(ExactLocation startLocation) {
        this.startLocation = startLocation;
    }

    public ExactLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(ExactLocation endLocation) {
        this.endLocation = endLocation;
    }

    public long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(long passengerId) {
        this.passengerId = passengerId;
    }
}
